package mc.replay.replay;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import mc.replay.api.MCReplayAPI;
import mc.replay.api.recording.IRecording;
import mc.replay.api.replay.IReplaySession;
import mc.replay.api.replay.session.IReplayPlayer;
import mc.replay.common.MCReplayInternal;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.replay.session.ReplayPlayer;
import mc.replay.replay.session.entity.ReplaySessionEntityHandler;
import mc.replay.replay.session.task.ReplaySessionInformTask;
import mc.replay.replay.session.task.ReplaySessionPlayTask;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
public final class ReplaySession implements IReplaySession {

    private final MCReplayInternal instance;

    private final UUID sessionUuid;
    private final IReplayPlayer navigator;
    private final Collection<IReplayPlayer> watchers;
    private final IRecording recording;

    @Setter
    private boolean paused = true;
    private double speed = 1.0;

    private final ReplaySessionEntityHandler entityCache;

    private final ReplaySessionPlayTask playTask;
    private final ReplaySessionInformTask informTask;

    @Getter(AccessLevel.NONE)
    private final BukkitTask playTaskHandle;
    @Getter(AccessLevel.NONE)
    private final BukkitTask informTaskHandle;

    private boolean invalid = false;

    ReplaySession(MCReplayInternal instance, Player navigator, Collection<Player> watchers, IRecording recording) {
        this.instance = instance;
        this.sessionUuid = UUID.randomUUID();
        this.recording = recording;
        this.watchers = new HashSet<>();

        this.navigator = new ReplayPlayer(navigator, this);
        for (Player watcher : watchers) {
            this.watchers.add(new ReplayPlayer(watcher, this));
        }

        this.entityCache = new ReplaySessionEntityHandler(this);

        this.playTask = new ReplaySessionPlayTask(instance, this);
        this.informTask = new ReplaySessionInformTask(this);

        this.playTaskHandle = Bukkit.getScheduler().runTaskTimer(MCReplayAPI.getJavaPlugin(), this.playTask, 0L, 1L);
        this.informTaskHandle = Bukkit.getScheduler().runTaskTimer(MCReplayAPI.getJavaPlugin(), this.informTask, 0L, 20L);
    }

    @Override
    public void stop() {
        this.playTaskHandle.cancel();
        this.informTaskHandle.cancel();

        this.invalid = true;
    }

    @Override
    public @NotNull Collection<IReplayPlayer> getAllPlayers() {
        Set<IReplayPlayer> allPlayers = new HashSet<>(this.watchers);
        allPlayers.add(this.navigator);
        return allPlayers;
    }

    @Override
    public boolean increaseSpeed() {
        double stepSize = this.speed >= 1 ? 1 : 0.25;
        this.speed = Math.min(this.speed + stepSize, 4.0);
        return true;
    }

    @Override
    public boolean decreaseSpeed() {
        double stepSize = this.speed <= 1 ? 0.25 : 1;
        this.speed = Math.max(this.speed - stepSize, 0.25);
        return true;
    }

    @Override
    public void updateInformationBar() {
        this.informTask.run();
    }

    public World getReplayWorld() {
        return this.navigator.player().getWorld();
    }

    public void sendPackets(List<ClientboundPacket> packets) {
        for (IReplayPlayer replayPlayer : this.getAllPlayers()) {
            for (ClientboundPacket packet : packets) {
                MCReplayAPI.getPacketLib().sendPacket(replayPlayer.player(), packet);
            }
        }
    }
}