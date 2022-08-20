package mc.replay.nms.v1_16_5.dispatcher.event.entity;

import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.replay.EntityId;
import org.bukkit.entity.Entity;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;

public class ReplayEntityDeathEventListener implements DispatcherEvent<EntityDeathEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public boolean ignoreCancelled() {
        return true;
    }

    @Override
    public Recordable getRecordable(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player || entity instanceof NPC) return null;

        // TODO check if entity is replay entity

        EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());
        return RecEntityDeath.of(entityId);
    }
}