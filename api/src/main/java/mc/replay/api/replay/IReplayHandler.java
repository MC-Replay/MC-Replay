package mc.replay.api.replay;

import mc.replay.api.recording.IRecording;
import mc.replay.api.replay.session.IReplayPlayer;
import mc.replay.api.replay.session.toolbar.IToolbarItemHandler;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public interface IReplayHandler {

    Map<UUID, IReplayPlayer> getReplayPlayers();

    Map<UUID, IReplaySession> getReplaySessions();

    IToolbarItemHandler getToolbarItemHandler();

    @NotNull IReplaySession startReplay(@NotNull IRecording recording, @NotNull Player navigator, @NotNull Player... watchers);

    boolean stopReplay(@NotNull UUID sessionUuid);

    @Nullable IReplayPlayer getReplayPlayer(@NotNull UUID uuid);

    @Nullable IReplayPlayer getReplayPlayer(@NotNull Player player);
}