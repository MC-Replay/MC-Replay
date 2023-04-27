package mc.replay.recording.dispatcher.dispatchers.event.entity;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.recording.dispatcher.dispatchers.DispatcherEvent;
import mc.replay.common.recordables.types.entity.action.RecEntitySwimming;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityToggleSwimEvent;

import java.util.List;

public final class ReplayEntityToggleSwimEventDispatcher implements DispatcherEvent<EntityToggleSwimEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable> getRecordables(EntityToggleSwimEvent event) {
        Entity entity = event.getEntity();

        EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());
        return List.of(
                new RecEntitySwimming(
                        entityId,
                        event.isSwimming()
                )
        );
    }
}