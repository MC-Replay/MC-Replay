package mc.replay.recording.dispatcher.dispatchers.event.entity;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.RecEntityGliding;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherEvent;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityToggleGlideEvent;

import java.util.List;

public final class ReplayEntityToggleGlideEventDispatcher extends DispatcherEvent<EntityToggleGlideEvent> {

    private ReplayEntityToggleGlideEventDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, EntityToggleGlideEvent event) {
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