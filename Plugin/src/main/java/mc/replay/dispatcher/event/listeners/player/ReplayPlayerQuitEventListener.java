package mc.replay.dispatcher.event.listeners.player;

import mc.replay.MCReplayPlugin;
import mc.replay.dispatcher.event.ReplayEventListener;
import mc.replay.recordables.entity.EntityId;
import mc.replay.recordables.entity.connection.RecPlayerQuit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

public class ReplayPlayerQuitEventListener implements ReplayEventListener<PlayerQuitEvent> {

    @Override
    public Class<PlayerQuitEvent> eventClass() {
        return PlayerQuitEvent.class;
    }

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public void listen(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        RecPlayerQuit recPlayerQuit = RecPlayerQuit.of(entityId);

        MCReplayPlugin.getInstance().getReplayStorage().addRecordable(recPlayerQuit);
    }
}