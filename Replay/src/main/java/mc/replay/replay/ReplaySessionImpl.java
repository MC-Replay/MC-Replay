package mc.replay.replay;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import mc.replay.api.MCReplayAPI;
import mc.replay.api.recording.Recording;
import mc.replay.api.replay.ReplaySession;
import mc.replay.api.replay.session.ReplayPlayer;
import mc.replay.common.utils.color.Text;
import mc.replay.replay.session.ReplayPlayerImpl;
import mc.replay.replay.session.task.ReplaySessionInformTask;
import mc.replay.replay.session.task.ReplaySessionPlayTask;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public final class ReplaySessionImpl implements ReplaySession {

    private final UUID sessionUuid;
    private final ReplayPlayer navigator;
    private final Collection<ReplayPlayer> watchers;
    private final Recording recording;

    @Setter
    private boolean paused = true;
    private double speed = 1.0;

    private final ReplaySessionPlayTask playTask;
    private final ReplaySessionInformTask informTask;

    @Getter(AccessLevel.NONE)
    private final BukkitTask playTaskHandle;
    @Getter(AccessLevel.NONE)
    private final BukkitTask informTaskHandle;

    private boolean invalid = false;

    ReplaySessionImpl(Player navigator, Collection<Player> watchers, Recording recording) {
        this.sessionUuid = UUID.randomUUID();
        this.recording = recording;
        this.watchers = new HashSet<>();

        this.navigator = new ReplayPlayerImpl(navigator, this);
        for (Player watcher : watchers) {
            this.watchers.add(new ReplayPlayerImpl(watcher, this));
        }

        this.playTask = new ReplaySessionPlayTask(this);
        this.informTask = new ReplaySessionInformTask(this);

        this.playTaskHandle = Bukkit.getScheduler().runTaskTimer(MCReplayAPI.getJavaPlugin(), this.playTask, 0L, 1L);
        this.informTaskHandle = Bukkit.getScheduler().runTaskTimer(MCReplayAPI.getJavaPlugin(), this.informTask, 0L, 20L);

        for (ReplayPlayer replayPlayer : this.getAllPlayers()) {
            MCReplayAPI.getReplayHandler().getToolbarItemHandler().giveItems(replayPlayer);
            boolean flying = replayPlayer.player().isFlying();
            replayPlayer.player().setAllowFlight(true);
            replayPlayer.player().setFlying(flying);
        }
    }

    @Override
    public void stop() {
        this.playTaskHandle.cancel();
        this.informTaskHandle.cancel();

        this.invalid = true;

        for (ReplayPlayer replayPlayer : this.getAllPlayers()) {
            replayPlayer.player().sendMessage(Text.color("&aReplay session stopped."));
        }
    }

    @Override
    public @NotNull Collection<ReplayPlayer> getAllPlayers() {
        Set<ReplayPlayer> allPlayers = new HashSet<>(this.watchers);
        allPlayers.add(this.navigator);
        return allPlayers;
    }

    @Override
    public boolean decreaseSpeed() {
        if (this.speed <= 0.25) return false;

        this.speed = (this.speed <= 1) ? this.speed - 0.25 : this.speed - 1;
        return true;
    }

    @Override
    public boolean increaseSpeed() {
        if (this.speed >= 4.0) return false;

        this.speed = (this.speed < 1) ? this.speed + 0.25 : this.speed + 1;
        return true;
    }

    @Override
    public void updateInformationBar() {
        this.informTask.run();
    }

    public World getReplayWorld() {
        return this.navigator.player().getWorld();
    }
}