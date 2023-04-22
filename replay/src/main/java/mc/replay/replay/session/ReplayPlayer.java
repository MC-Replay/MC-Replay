package mc.replay.replay.session;

import mc.replay.api.replay.session.IReplayPlayer;
import mc.replay.replay.ReplaySession;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record ReplayPlayer(Player player, ReplaySession replaySession) implements IReplayPlayer {

    @Override
    public @NotNull ReplaySession replaySession() {
        return this.replaySession;
    }

    @Override
    public boolean isNavigator() {
        return this.replaySession.getNavigator().player().equals(this.player);
    }

    @Override
    public boolean isWatcher() {
        return !this.isNavigator();
    }
}