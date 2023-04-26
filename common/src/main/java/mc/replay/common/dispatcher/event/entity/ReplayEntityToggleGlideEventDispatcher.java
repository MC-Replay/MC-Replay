package mc.replay.common.dispatcher.event.entity;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.recordables.types.entity.action.RecEntityGliding;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityToggleGlideEvent;

import java.util.List;

public final class ReplayEntityToggleGlideEventDispatcher implements DispatcherEvent<EntityToggleGlideEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable> getRecordables(EntityToggleGlideEvent event) {
        Entity entity = event.getEntity();

        EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());
        return List.of(
                new RecEntityGliding(
                        entityId,
                        event.isGliding()
                )
        );
    }
}