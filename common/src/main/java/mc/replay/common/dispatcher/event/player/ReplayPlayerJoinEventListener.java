package mc.replay.common.dispatcher.event.player;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.recordables.entity.RecPlayerSpawn;
import mc.replay.wrapper.entity.PlayerWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.function.Function;

public final class ReplayPlayerJoinEventListener implements DispatcherEvent<PlayerJoinEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerWrapper playerWrapper = new PlayerWrapper(player);
        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(new RecPlayerSpawn(entityId, playerWrapper));
    }
}