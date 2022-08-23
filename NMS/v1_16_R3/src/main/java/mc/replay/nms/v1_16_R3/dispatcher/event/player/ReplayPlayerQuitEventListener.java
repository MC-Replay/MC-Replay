package mc.replay.nms.v1_16_R3.dispatcher.event.player;

import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.nms.global.recordable.RecPlayerDestroy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.function.Function;

public final class ReplayPlayerQuitEventListener implements DispatcherEvent<PlayerQuitEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(RecPlayerDestroy.of(entityId));
    }
}