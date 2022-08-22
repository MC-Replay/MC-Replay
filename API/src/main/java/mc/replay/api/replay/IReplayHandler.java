package mc.replay.api.replay;

import mc.replay.api.recording.Recording;
import mc.replay.api.replay.session.ReplayPlayer;
import mc.replay.api.replay.session.toolbar.IToolbarItemHandler;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public interface IReplayHandler {

    Map<UUID, ReplayPlayer> getReplayPlayers();

    Map<UUID, ReplaySession> getReplaySessions();

    IToolbarItemHandler getToolbarItemHandler();

    @NotNull ReplaySession startReplay(@NotNull Recording recording, @NotNull Player navigator, @NotNull Player... watchers);

    boolean stopReplay(@NotNull UUID sessionUuid);

    @Nullable ReplayPlayer getReplayPlayer(@NotNull UUID uuid);

    @Nullable ReplayPlayer getReplayPlayer(@NotNull Player player);
}