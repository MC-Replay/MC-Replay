package mc.replay.nms.v1_16_5.dispatcher.event.entity;

import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.replay.EntityId;
import mc.replay.nms.v1_16_5.recordable.entity.action.RecEntitySwimming;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityToggleSwimEvent;

import java.util.List;

public class ReplayEntityToggleSwimEventListener implements DispatcherEvent<EntityToggleSwimEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public boolean ignoreCancelled() {
        return true;
    }

    @Override
    public List<Recordable> getRecordable(EntityToggleSwimEvent event) {
        Entity entity = event.getEntity();

        EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());
        return List.of(RecEntitySwimming.of(entityId, event.isSwimming()));
    }
}