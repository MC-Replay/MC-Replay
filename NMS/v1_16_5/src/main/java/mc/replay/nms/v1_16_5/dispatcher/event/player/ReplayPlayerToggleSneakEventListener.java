package mc.replay.nms.v1_16_5.dispatcher.event.player;

import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.replay.EntityId;
import mc.replay.nms.v1_16_5.recordable.entity.action.RecEntitySneaking;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.List;

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
    public List<Recordable> getRecordable(Object eventClass) {
        PlayerToggleSneakEvent event = (PlayerToggleSneakEvent) eventClass;
        Player player = event.getPlayer();

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(RecEntitySneaking.of(entityId, event.isSneaking()));
    }
}