package mc.replay.common.dispatcher.event.player;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.recordables.types.entity.action.RecEntitySprinting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import java.util.List;

public final class ReplayPlayerToggleSprintEventDispatcher implements DispatcherEvent<PlayerToggleSprintEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable> getRecordables(PlayerToggleSprintEvent event) {
        Player player = event.getPlayer();

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(
                new RecEntitySprinting(
                        entityId,
                        event.isSprinting()
                )
        );
    }
}