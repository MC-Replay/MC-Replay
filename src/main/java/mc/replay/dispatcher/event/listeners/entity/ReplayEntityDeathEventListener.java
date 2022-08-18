package mc.replay.dispatcher.event.listeners.entity;

import mc.replay.MCReplay;
import mc.replay.dispatcher.event.ReplayEventListener;
import mc.replay.recordables.entity.EntityId;
import mc.replay.recordables.entity.spawn.RecEntityDeath;
import org.bukkit.entity.Entity;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;

public class ReplayEntityDeathEventListener implements ReplayEventListener<EntityDeathEvent> {

    @Override
    public Class<EntityDeathEvent> eventClass() {
        return EntityDeathEvent.class;
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
    public void listen(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player || entity instanceof NPC) return;

        // TODO check if entity is replay entity

        EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());
        RecEntityDeath recordable = RecEntityDeath.of(entityId);

        MCReplay.getInstance().getReplayStorage().addRecordable(recordable);
    }
}