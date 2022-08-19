package mc.replay.replay.session;

import lombok.Getter;
import lombok.Setter;
import mc.replay.MCReplayPlugin;
import mc.replay.api.recordable.Recordable;
import mc.replay.recordables.block.BlockRecordable;
import mc.replay.recordables.entity.EntityRecordable;
import mc.replay.recordables.entity.connection.RecPlayerJoin;
import mc.replay.recordables.entity.spawn.RecEntitySpawn;
import mc.replay.recordables.particle.ParticleRecordable;
import mc.replay.recordables.sound.SoundRecordable;
import mc.replay.recordables.world.WorldEventRecordable;
import mc.replay.replay.entity.ReplayEntity;
import mc.replay.utils.EntityPacketUtils;
import mc.replay.utils.color.Text;
import mc.replay.utils.reflection.nms.MinecraftPlayerNMS;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public final class ReplaySession {

    private final NavigableMap<Long, List<Recordable>> recordables;
    private final List<ReplayPlayer> replayPlayers;

    @Getter
    private final Collection<ReplayEntity<?>> entities;

    private final long startTime;
    private final long endTime;

    @Getter
    @Setter
    private long currentTime;

    @Getter
    @Setter
    private double speed = 1D;

    @Getter
    @Setter
    private boolean paused = true;

    private BukkitTask task;
    private BukkitTask informTask;

    public ReplaySession(NavigableMap<Long, List<Recordable>> recordables, List<Player> targets) {
        this.recordables = recordables;
        this.replayPlayers = new ArrayList<>();
        for (Player target : targets) {
            ReplayPlayer player = new ReplayPlayer(target, this);
            player.setReplayItems();

            this.replayPlayers.add(player);
        }

        this.entities = new HashSet<>();

        this.startTime = this.currentTime = recordables.firstKey();
        this.endTime = recordables.lastKey();

        for (Player target : targets) {
            boolean flying = target.isFlying();
            target.setGameMode(GameMode.ADVENTURE);
            target.setAllowFlight(true);
            target.setFlying(flying);
        }

        this.task = Bukkit.getScheduler().runTaskTimer(MCReplayPlugin.getInstance(), () -> {
            if (this.paused) return;

            List<Recordable> records = new ArrayList<>();
            long nextTime = this.currentTime + ((long) (Math.ceil(this.speed * 50D)));
            for (long i = this.currentTime; i < nextTime; i++) {
                records.addAll(this.recordables.getOrDefault(i, new ArrayList<>()));
            }

            for (Recordable recordable : records) {
                if (recordable instanceof EntityRecordable entityRecordable) {
                    if (entityRecordable instanceof RecPlayerJoin || entityRecordable instanceof RecEntitySpawn) {
                        ReplayEntity<?> replayEntityPlayer = entityRecordable.play(null, null, null, -1);
                        replayEntityPlayer.addViewers(this.getTargets());

                        this.entities.add(replayEntityPlayer.spawn());
                        continue;
                    }

                    this.entities.removeIf((entity) -> {
                        if (!entityRecordable.match(entity.getOriginalEntityId())) return false;

                        for (Map.Entry<Player, Object> entry : entity.getViewers().entrySet()) {
                            Object entityPlayer = entry.getValue();
                            if (entityPlayer == null) continue;

                            int entityId = EntityPacketUtils.getEntityId(entityPlayer);
                            if (entityId == -1) continue;

                            if (entityRecordable.play(entry.getKey(), entity, entityPlayer, entityId) == null) {
                                return true;
                            }
                        }

                        return false;
                    });
                } else if (recordable instanceof BlockRecordable blockRecordable) {
                    for (Player target : this.getTargets()) {
                        if (target == null || !target.isOnline()) continue;

                        blockRecordable.play(target);
                    }
                } else if (recordable instanceof SoundRecordable soundRecordable) {
                    for (Player target : this.getTargets()) {
                        if (target == null || !target.isOnline()) continue;

                        soundRecordable.play(target);
                    }
                } else if (recordable instanceof ParticleRecordable particleRecordable) {
                    for (Player target : this.getTargets()) {
                        if (target == null || !target.isOnline()) continue;

                        particleRecordable.play(target);
                    }
                } else if (recordable instanceof WorldEventRecordable worldEventRecordable) {
                    for (Player target : this.getTargets()) {
                        if (target == null || !target.isOnline()) continue;

                        worldEventRecordable.play(target);
                    }
                }
            }

            this.currentTime += nextTime - this.currentTime;
            if (this.currentTime >= this.endTime) {
                this.stop();

                for (Player target : this.getTargets()) {
                    target.sendMessage(Text.color("&aReplay stopped!"));
                }
            }
        }, 0, 1);

        this.informTask = Bukkit.getScheduler().runTaskTimer(MCReplayPlugin.getInstance(), () -> {
            String status = this.paused ? "&cPaused" : "&aPlaying";
            String time = DurationFormatUtils.formatDuration(Math.max(0, this.currentTime - this.startTime), "mm:ss");
            String duration = DurationFormatUtils.formatDuration(Math.min(this.endTime, this.endTime - this.startTime), "mm:ss");
            String speed = this.speed + "x";

            for (Player pl : this.getTargets()) {
                MinecraftPlayerNMS.sendActionbar(pl, Text.color(status + "     &e" + time + " / " + duration + "     &6" + speed));
            }
        }, 0, 20);
    }

    public void stop() {
        this.task.cancel();
        this.task = null;

        this.informTask.cancel();
        this.informTask = null;

        for (ReplayEntity<?> entity : this.entities) {
            entity.destroy();
        }

        for (ReplayPlayer replayPlayer : this.replayPlayers) {
            replayPlayer.restoreInventory();
            replayPlayer.getPlayer().closeInventory();

            MinecraftPlayerNMS.sendActionbar(replayPlayer.getPlayer(), "");
        }
    }

    public List<Player> getTargets() {
        return this.replayPlayers.stream().map(ReplayPlayer::getPlayer).collect(Collectors.toList());
    }

    public ReplayPlayer getReplayPlayer(Player player) {
        for (ReplayPlayer replayPlayer : this.replayPlayers) {
            if (replayPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                return replayPlayer;
            }
        }
        return null;
    }

    public void jumpForwards(int seconds) {
        long endMillis = this.currentTime + TimeUnit.SECONDS.toMillis(seconds);
        if (endMillis >= this.endTime) {
            this.currentTime = this.endTime;
            return;
        }

        Map<Long, List<Recordable>> records = new HashMap<>();

        for (long i = this.currentTime; i < endMillis; i++) {
            List<Recordable> recordables = this.recordables.getOrDefault(i, new ArrayList<>());

            if (recordables != null && !recordables.isEmpty()) {
                records.put(i, recordables);
            }
        }

        for (List<Recordable> value : records.values()) {
            for (Recordable recordable : value) {
                if (recordable instanceof EntityRecordable entityRecordable && (entityRecordable instanceof RecPlayerJoin || entityRecordable instanceof RecEntitySpawn)) {
                    ReplayEntity<?> replayEntityPlayer = entityRecordable.play(null, null, null, -1);
                    replayEntityPlayer.addViewers(this.getTargets());

                    this.entities.add(replayEntityPlayer.spawn());
                }
            }
        }

        this.entities.removeIf((entity) -> {
            NavigableMap<Long, List<Recordable>> recordables = MCReplayPlugin.getInstance().getReplayStorage().getTypeRecordables(records, EntityRecordable.class, entity.getOriginalEntityId());
            Collection<String> finishedRecordables = new HashSet<>();

            Long lastIndex = recordables.lastKey();
            for (long i = (lastIndex == null) ? -1 : lastIndex; i >= 0; i--) {
                for (Recordable recordable : recordables.getOrDefault(i, new ArrayList<>())) {
                    if (!finishedRecordables.contains(recordable.getClass().getSimpleName())) {
                        for (Map.Entry<Player, Object> entry : entity.getViewers().entrySet()) {
                            Object entityPlayer = entry.getValue();
                            if (entityPlayer == null) continue;

                            int entityId = EntityPacketUtils.getEntityId(entityPlayer);
                            if (entityId == -1) continue;

                            finishedRecordables.add(recordable.getClass().getSimpleName());

                            if (((EntityRecordable) recordable).jumpInTime(entry.getKey(), entity, entityPlayer, entityId, true) == null) {
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        });

        this.currentTime += endMillis - this.currentTime;
    }

    public void jumpBackwards(int seconds) {
        long startMillis = Math.max(this.currentTime - TimeUnit.SECONDS.toMillis(seconds), this.startTime);

        Map<Long, List<Recordable>> records = new HashMap<>();

        for (long i = startMillis; i < this.currentTime; i++) {
            List<Recordable> recordables = this.recordables.getOrDefault(i, new ArrayList<>());

            if (recordables != null && !recordables.isEmpty()) {
                records.put(i, recordables);
            }
        }

        this.entities.removeIf((entity) -> {
            NavigableMap<Long, List<Recordable>> recordables = MCReplayPlugin.getInstance().getReplayStorage().getTypeRecordables(records, EntityRecordable.class, entity.getOriginalEntityId());
            Collection<String> finishedRecordables = new HashSet<>();

            Long firstIndex = recordables.firstKey();
            for (long i = 0; i <= ((firstIndex == null) ? -1 : firstIndex); i++) {
                for (Recordable recordable : recordables.getOrDefault(i, new ArrayList<>())) {
                    if (!finishedRecordables.contains(recordable.getClass().getSimpleName())) {
                        for (Map.Entry<Player, Object> entry : entity.getViewers().entrySet()) {
                            Object entityPlayer = entry.getValue();
                            if (entityPlayer == null) continue;

                            int entityId = EntityPacketUtils.getEntityId(entityPlayer);
                            if (entityId == -1) continue;

                            finishedRecordables.add(recordable.getClass().getSimpleName());

                            if (((EntityRecordable) recordable).jumpInTime(entry.getKey(), entity, entityPlayer, entityId, false) == null) {
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        });

        this.currentTime = startMillis;
    }
}