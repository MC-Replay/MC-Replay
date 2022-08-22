package mc.replay.api.replay.session;

import mc.replay.api.replay.ReplaySession;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface ReplayPlayer {

    @NotNull Player player();

    @NotNull ReplaySession replaySession();

    boolean isNavigator();

    boolean isWatcher();
}