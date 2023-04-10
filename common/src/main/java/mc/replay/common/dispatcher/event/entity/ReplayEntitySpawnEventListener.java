package mc.replay.common.dispatcher.event.entity;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.recordables.entity.RecEntitySpawnMetadata;
import mc.replay.wrapper.entity.EntityWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;
import java.util.function.Function;

public final class ReplayEntitySpawnEventListener implements DispatcherEvent<EntitySpawnEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player || entity instanceof NPC) return null;

        EntityWrapper entityWrapper = new EntityWrapper(entity);

        EntityId entityId = EntityId.of(entity.getEntityId());
        return List.of(new RecEntitySpawnMetadata(entityId, entityWrapper.getMetadata().getEntries()));
    }
}