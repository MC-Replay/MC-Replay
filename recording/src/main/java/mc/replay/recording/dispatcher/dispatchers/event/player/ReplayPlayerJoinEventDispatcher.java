package mc.replay.recording.dispatcher.dispatchers.event.player;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.RecPlayerSpawn;
import mc.replay.nms.entity.player.RPlayer;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherEvent;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public final class ReplayPlayerJoinEventDispatcher extends DispatcherEvent<PlayerJoinEvent> {

    private ReplayPlayerJoinEventDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, PlayerJoinEvent event) {
        Player player = event.getPlayer();

        RPlayer playerWrapper = new RPlayer(player);
        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(
                new RecPlayerSpawn(
                        entityId,
                        playerWrapper
                )
        );
    }
}