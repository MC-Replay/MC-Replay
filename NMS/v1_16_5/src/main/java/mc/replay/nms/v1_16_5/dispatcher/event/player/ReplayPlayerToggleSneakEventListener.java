package mc.replay.nms.v1_16_5.dispatcher.event.player;

import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.replay.EntityId;
import mc.replay.recordables.entity.action.RecEntitySneaking;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class ReplayPlayerToggleSneakEventListener implements DispatcherEvent<PlayerToggleSneakEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public boolean ignoreCancelled() {
        return true;
    }

    @Override
    public Recordable getRecordable(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return RecEntitySneaking.of(entityId, event.isSneaking());
    }
}