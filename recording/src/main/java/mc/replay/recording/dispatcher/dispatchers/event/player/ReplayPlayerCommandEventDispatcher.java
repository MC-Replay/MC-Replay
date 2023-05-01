package mc.replay.recording.dispatcher.dispatchers.event.player;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.chat.RecPlayerCommand;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherEvent;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public final class ReplayPlayerCommandEventDispatcher extends DispatcherEvent<PlayerCommandPreprocessEvent> {

    private ReplayPlayerCommandEventDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(new RecPlayerCommand(entityId, player.getName(), event.getMessage()));
    }
}