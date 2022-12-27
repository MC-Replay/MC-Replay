package mc.replay.api.replay.session.toolbar;

import mc.replay.api.replay.session.ReplayPlayer;
import org.jetbrains.annotations.NotNull;

public interface IToolbarItemHandler {

    void giveItems(@NotNull ReplayPlayer replayPlayer);
}