package mc.replay.dispatcher.event.listeners.entity;

import mc.replay.MCReplayPlugin;
import mc.replay.dispatcher.event.ReplayEventListener;
import mc.replay.common.replay.EntityId;
import mc.replay.recordables.entity.action.RecEntitySwimming;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityToggleSwimEvent;

public class ReplayEntityToggleSwimEventListener implements ReplayEventListener<EntityToggleSwimEvent> {

    @Override
    public Class<EntityToggleSwimEvent> eventClass() {
        return EntityToggleSwimEvent.class;
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
    public void listen(EntityToggleSwimEvent event) {
        Entity entity = event.getEntity();

        EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());
        RecEntitySwimming recordable = RecEntitySwimming.of(entityId, event.isSwimming());

        MCReplayPlugin.getInstance().getReplayStorage().addRecordable(recordable);
    }
}