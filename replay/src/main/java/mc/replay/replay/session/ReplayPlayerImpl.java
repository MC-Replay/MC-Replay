package mc.replay.replay.session;

import mc.replay.api.replay.session.ReplayPlayer;
import mc.replay.replay.ReplaySessionImpl;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record ReplayPlayerImpl(Player player, ReplaySessionImpl replaySession) implements ReplayPlayer {

    @Override
    public @NotNull ReplaySessionImpl replaySession() {
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