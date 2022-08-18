package mc.replay.dispatcher.event.listeners.entity;

import mc.replay.MCReplay;
import mc.replay.dispatcher.event.ReplayEventListener;
import mc.replay.recordables.entity.EntityId;
import mc.replay.recordables.entity.action.RecEntityGliding;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityToggleGlideEvent;

public class ReplayEntityToggleGlideEventListener implements ReplayEventListener<EntityToggleGlideEvent> {

    @Override
    public Class<EntityToggleGlideEvent> eventClass() {
        return EntityToggleGlideEvent.class;
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
    public void listen(EntityToggleGlideEvent event) {
        Entity entity = event.getEntity();

        EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());
        RecEntityGliding recordable = RecEntityGliding.of(entityId, event.isGliding());

        MCReplay.getInstance().getReplayStorage().addRecordable(recordable);
    }
}