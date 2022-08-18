package mc.replay.dispatcher.event.listeners.player;

import mc.replay.MCReplayPlugin;
import mc.replay.dispatcher.event.ReplayEventListener;
import mc.replay.recordables.entity.EntityId;
import mc.replay.recordables.entity.action.RecEntitySneaking;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class ReplayPlayerToggleSneakEventListener implements ReplayEventListener<PlayerToggleSneakEvent> {

    @Override
    public Class<PlayerToggleSneakEvent> eventClass() {
        return PlayerToggleSneakEvent.class;
    }

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public boolean ignoreCancelled() {
        return true;
    }

    @Override
    public void listen(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        RecEntitySneaking recordable = RecEntitySneaking.of(entityId, event.isSneaking());

        MCReplayPlugin.getInstance().getReplayStorage().addRecordable(recordable);
    }
}