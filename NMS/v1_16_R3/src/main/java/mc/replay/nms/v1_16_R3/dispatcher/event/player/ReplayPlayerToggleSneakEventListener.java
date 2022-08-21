package mc.replay.nms.v1_16_R3.dispatcher.event.player;

import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.replay.EntityId;
import mc.replay.nms.v1_16_R3.recordable.entity.action.RecEntitySneaking;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.List;

public final class ReplayPlayerToggleSneakEventListener implements DispatcherEvent<PlayerToggleSneakEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable> getRecordables(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(RecEntitySneaking.of(entityId, event.isSneaking()));
    }
}