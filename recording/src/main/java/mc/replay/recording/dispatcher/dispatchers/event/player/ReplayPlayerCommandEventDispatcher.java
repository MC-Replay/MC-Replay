package mc.replay.recording.dispatcher.dispatchers.event.player;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.recording.dispatcher.dispatchers.DispatcherEvent;
import mc.replay.common.recordables.types.chat.RecPlayerCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public final class ReplayPlayerCommandEventDispatcher implements DispatcherEvent<PlayerCommandPreprocessEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable> getRecordables(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(new RecPlayerCommand(entityId, player.getName(), event.getMessage()));
    }
}