package mc.replay.replay.session;

import mc.replay.api.replay.ReplaySession;
import mc.replay.api.replay.session.ReplayPlayer;
import org.bukkit.entity.Player;

public record ReplayPlayerImpl(Player player, ReplaySession replaySession) implements ReplayPlayer {

    @Override
    public boolean isNavigator() {
        return this.replaySession.getNavigator().player().equals(this.player);
    }

    @Override
    public boolean isWatcher() {
        return !this.isNavigator();
    }
}