package mc.replay.dispatcher.event.listeners.entity;

import mc.replay.MCReplayPlugin;
import mc.replay.dispatcher.event.ReplayEventListener;
import mc.replay.common.replay.EntityId;
import mc.replay.recordables.entity.spawn.RecEntitySpawn;
import org.bukkit.entity.Entity;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ReplayEntitySpawnEventListener implements ReplayEventListener<EntitySpawnEvent> {

    @Override
    public Class<EntitySpawnEvent> eventClass() {
        return EntitySpawnEvent.class;
    }

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public boolean ignoreCancelled() {
        return true;
    }

    @Override
    public void listen(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player || entity instanceof NPC) return;

        // TODO check if entity is replay entity

        EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());
        RecEntitySpawn recordable = RecEntitySpawn.of(entityId, entity.getType(), entity.getLocation());

        MCReplayPlugin.getInstance().getReplayStorage().addRecordable(recordable);
    }
}