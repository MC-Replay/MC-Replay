package mc.replay.nms.v1_16_5.dispatcher.event.player;

import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.replay.EntityId;
import mc.replay.nms.global.recordable.RecPlayerQuit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class ReplayPlayerQuitEventListener implements DispatcherEvent<PlayerQuitEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable> getRecordable(Object eventClass) {
        PlayerQuitEvent event = (PlayerQuitEvent) eventClass;
        Player player = event.getPlayer();

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(RecPlayerQuit.of(entityId));
    }
}