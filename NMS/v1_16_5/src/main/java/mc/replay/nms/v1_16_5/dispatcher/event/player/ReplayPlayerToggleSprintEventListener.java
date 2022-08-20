package mc.replay.nms.v1_16_5.dispatcher.event.player;

import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.replay.EntityId;
import mc.replay.recordables.entity.action.RecEntitySprinting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class ReplayPlayerToggleSprintEventListener implements DispatcherEvent<PlayerToggleSprintEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public boolean ignoreCancelled() {
        return true;
    }

    @Override
    public Recordable getRecordable(PlayerToggleSprintEvent event) {
        Player player = event.getPlayer();

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return RecEntitySprinting.of(entityId, event.isSprinting());
    }
}