package mc.replay.common.dispatcher.event.entity;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.recordables.types.entity.RecEntityDestroy;
import org.bukkit.entity.Entity;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

public final class ReplayEntityDeathEventDispatcher implements DispatcherEvent<EntityDeathEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable> getRecordables(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player || entity instanceof NPC) return null;

        EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());
        return List.of(
                new RecEntityDestroy(
                        entityId
                )
        );
    }
}