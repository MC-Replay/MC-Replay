package mc.replay.replay;

import lombok.Getter;
import mc.replay.api.recording.Recording;
import mc.replay.api.replay.IReplayHandler;
import mc.replay.api.replay.ReplaySession;
import mc.replay.api.replay.session.ReplayPlayer;
import mc.replay.replay.session.ReplayPlayerImpl;
import mc.replay.replay.session.toolbar.ToolbarItemHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public final class ReplayHandler implements IReplayHandler {

    private final Map<UUID, ReplayPlayer> replayPlayers = new HashMap<>();
    private final Map<UUID, ReplaySession> replaySessions = new HashMap<>();

    private final ToolbarItemHandler toolbarItemHandler;

    public ReplayHandler(JavaPlugin plugin) {
        this.toolbarItemHandler = new ToolbarItemHandler(this, plugin);
    }

    @Override
    public @NotNull ReplaySession startReplay(@NotNull Recording recording, @NotNull Player navigator, @NotNull Player... watchers) {
        ReplaySessionImpl replaySession = new ReplaySessionImpl(navigator, Arrays.asList(watchers), recording);
        this.replaySessions.put(replaySession.getSessionUuid(), replaySession);

        for (ReplayPlayer player : replaySession.getAllPlayers()) {
            this.replayPlayers.put(player.player().getUniqueId(), player);
        }

        return replaySession;
    }

    @Override
    public boolean stopReplay(@NotNull UUID sessionUuid) {
        ReplaySession session = this.replaySessions.remove(sessionUuid);
        if (session == null) return false;

        for (ReplayPlayer player : session.getAllPlayers()) {
            this.replayPlayers.remove(player.player().getUniqueId());
        }

        session.stop();
        return true;
    }

    @Override
    public @Nullable ReplayPlayerImpl getReplayPlayer(@NotNull UUID uuid) {
        ReplayPlayerImpl replayPlayer = (ReplayPlayerImpl) this.replayPlayers.get(uuid);
        return (replayPlayer == null || replayPlayer.replaySession().isInvalid()) ? null : replayPlayer;
    }

    @Override
    public @Nullable ReplayPlayerImpl getReplayPlayer(@NotNull Player player) {
        return this.getReplayPlayer(player.getUniqueId());
    }
}