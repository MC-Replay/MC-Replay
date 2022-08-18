package mc.replay.dispatcher.event.listeners.player;

import mc.replay.MCReplayPlugin;
import mc.replay.dispatcher.event.ReplayEventListener;
import mc.replay.recordables.entity.EntityId;
import mc.replay.recordables.entity.action.RecEntitySprinting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class ReplayPlayerToggleSprintEventListener implements ReplayEventListener<PlayerToggleSprintEvent> {

    @Override
    public Class<PlayerToggleSprintEvent> eventClass() {
        return PlayerToggleSprintEvent.class;
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
    public void listen(PlayerToggleSprintEvent event) {
        Player player = event.getPlayer();

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        RecEntitySprinting recordable = RecEntitySprinting.of(entityId, event.isSprinting());

        MCReplayPlugin.getInstance().getReplayStorage().addRecordable(recordable);
    }
}