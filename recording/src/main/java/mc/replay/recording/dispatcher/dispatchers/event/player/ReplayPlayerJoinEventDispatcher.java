package mc.replay.recording.dispatcher.dispatchers.event.player;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.RecPlayerSpawn;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherEvent;
import mc.replay.wrapper.entity.PlayerWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public final class ReplayPlayerJoinEventDispatcher implements DispatcherEvent<PlayerJoinEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerWrapper playerWrapper = new PlayerWrapper(player);
        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(
                new RecPlayerSpawn(
                        entityId,
                        playerWrapper
                )
        );
    }
}