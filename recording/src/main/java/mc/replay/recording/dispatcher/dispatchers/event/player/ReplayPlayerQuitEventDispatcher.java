package mc.replay.recording.dispatcher.dispatchers.event.player;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.RecPlayerDestroy;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherEvent;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public final class ReplayPlayerQuitEventDispatcher extends DispatcherEvent<PlayerQuitEvent> {

    private ReplayPlayerQuitEventDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, PlayerQuitEvent event) {
        Player player = event.getPlayer();

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(
                new RecPlayerDestroy(
                        entityId
                )
        );
    }
}