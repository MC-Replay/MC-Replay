package mc.replay.common.dispatcher.event.entity;

import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.entity.action.RecEntityGliding;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityToggleGlideEvent;

import java.util.List;
import java.util.function.Function;

public final class ReplayEntityToggleGlideEventListener implements DispatcherEvent<EntityToggleGlideEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(EntityToggleGlideEvent event) {
        Entity entity = event.getEntity();

        EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());
        return List.of(RecEntityGliding.of(entityId, event.isGliding()));
    }
}