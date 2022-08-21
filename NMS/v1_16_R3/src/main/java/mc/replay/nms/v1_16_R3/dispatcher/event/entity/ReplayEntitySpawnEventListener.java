package mc.replay.nms.v1_16_R3.dispatcher.event.entity;

import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.replay.EntityId;
import mc.replay.nms.global.recordable.RecEntitySpawn;
import org.bukkit.entity.Entity;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;

public class ReplayEntitySpawnEventListener implements DispatcherEvent<EntitySpawnEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable> getRecordable(Object eventClass) {
        EntitySpawnEvent event = (EntitySpawnEvent) eventClass;

        Entity entity = event.getEntity();
        if (entity instanceof Player || entity instanceof NPC) return null;

        // TODO check if entity is replay entity

        EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());
        return List.of(RecEntitySpawn.of(entityId, entity.getType(), entity.getLocation()));
    }
}