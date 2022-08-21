package mc.replay.nms.v1_16_R3.dispatcher.event.player;

import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.replay.EntityId;
import mc.replay.nms.v1_16_R3.recordable.entity.action.RecEntitySprinting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import java.util.List;

public final class ReplayPlayerToggleSprintEventListener implements DispatcherEvent<PlayerToggleSprintEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable> getRecordables(PlayerToggleSprintEvent event) {
        Player player = event.getPlayer();

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(RecEntitySprinting.of(entityId, event.isSprinting()));
    }
}