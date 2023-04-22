package mc.replay.replay;

import lombok.Getter;
import mc.replay.api.recording.IRecording;
import mc.replay.api.replay.IReplayHandler;
import mc.replay.api.replay.IReplaySession;
import mc.replay.api.replay.session.IReplayPlayer;
import mc.replay.common.MCReplayInternal;
import mc.replay.replay.preparation.ReplayPlayerPreparationHandler;
import mc.replay.replay.session.ReplayPlayer;
import mc.replay.replay.session.listener.ReplaySessionPacketListener;
import mc.replay.replay.session.toolbar.ToolbarItemHandler;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public final class ReplayHandler implements IReplayHandler {

    private final MCReplayInternal instance;
    private final Map<UUID, IReplayPlayer> replayPlayers = new HashMap<>();
    private final Map<UUID, IReplaySession> replaySessions = new HashMap<>();

    private final ReplayPlayerPreparationHandler preparationHandler;

    private final ReplaySessionPacketListener packetListener;
    private final ToolbarItemHandler toolbarItemHandler;

    public ReplayHandler(MCReplayInternal instance) {
        this.instance = instance;
        this.preparationHandler = new ReplayPlayerPreparationHandler(this, instance);
        this.packetListener = new ReplaySessionPacketListener(this, instance);
        this.toolbarItemHandler = new ToolbarItemHandler(this, instance.getJavaPlugin());
    }

    @Override
    public @NotNull IReplaySession startReplay(@NotNull IRecording recording, @NotNull Player navigator, @NotNull Player... watchers) {
        ReplaySession replaySession = new ReplaySession(this.instance, navigator, Arrays.asList(watchers), recording);
        this.replaySessions.put(replaySession.getSessionUuid(), replaySession);

        for (IReplayPlayer player : replaySession.getAllPlayers()) {
            this.replayPlayers.put(player.player().getUniqueId(), player);
            this.preparationHandler.prepare(player);
        }

        return replaySession;
    }

    @Override
    public boolean stopReplay(@NotNull UUID sessionUuid) {
        IReplaySession session = this.replaySessions.remove(sessionUuid);
        if (session == null) return false;

        for (IReplayPlayer player : session.getAllPlayers()) {
            this.replayPlayers.remove(player.player().getUniqueId());
            this.preparationHandler.reset(player);
        }

        session.stop();
        return true;
    }

    @Override
    public @Nullable ReplayPlayer getReplayPlayer(@NotNull UUID uuid) {
        ReplayPlayer replayPlayer = (ReplayPlayer) this.replayPlayers.get(uuid);
        return (replayPlayer == null || replayPlayer.replaySession().isInvalid()) ? null : replayPlayer;
    }

    @Override
    public @Nullable ReplayPlayer getReplayPlayer(@NotNull Player player) {
        return this.getReplayPlayer(player.getUniqueId());
    }
}