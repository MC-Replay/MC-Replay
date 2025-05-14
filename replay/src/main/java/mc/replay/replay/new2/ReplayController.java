package mc.replay.replay.new2;

import mc.replay.api.recording.IRecording;
import mc.replay.api.replay.IReplayController;
import mc.replay.api.replay.IReplaySession;
import mc.replay.api.replay.session.IReplayPlayer;
import mc.replay.api.replay.session.toolbar.IToolbarController;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public final class ReplayController implements IReplayController {

    private final ReplayService service;

    ReplayController() {
        this.service = new ReplayService();
    }

    @Override
    public Map<UUID, IReplayPlayer> getReplayPlayers() {
        return Map.of();
    }

    @Override
    public Map<UUID, IReplaySession> getReplaySessions() {
        return Map.of();
    }

    @Override
    public IToolbarController getToolbarItemHandler() {
        return null;
    }

    @Override
    public @NotNull IReplaySession startReplay(@NotNull IRecording recording, @NotNull Player navigator, @NotNull Player... watchers) {
        return null;
    }

    @Override
    public boolean stopReplay(@NotNull UUID sessionUuid) {
        return false;
    }

    @Override
    public @Nullable IReplayPlayer getReplayPlayer(@NotNull UUID uuid) {
        return null;
    }

    @Override
    public @Nullable IReplayPlayer getReplayPlayer(@NotNull Player player) {
        return null;
    }
}