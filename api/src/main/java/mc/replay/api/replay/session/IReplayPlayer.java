package mc.replay.api.replay.session;

import mc.replay.api.replay.IReplaySession;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface IReplayPlayer {

    @NotNull Player player();

    @NotNull IReplaySession replaySession();

    boolean isNavigator();

    boolean isWatcher();
}