//package mc.replay.common.dispatcher.event.player;
//
//import mc.replay.common.dispatcher.DispatcherEvent;
//import mc.replay.api.recording.recordables.Recordable;
//import mc.replay.api.recording.recordables.entity.EntityId;
//import mc.replay.nms.v1_16_R3.recordable.entity.action.RecEntitySneaking;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventPriority;
//import org.bukkit.event.player.PlayerToggleSneakEvent;
//
//import java.util.List;
//import java.util.function.Function;
//
//public final class ReplayPlayerToggleSneakEventListener implements DispatcherEvent<PlayerToggleSneakEvent> {
//
//    @Override
//    public EventPriority getPriority() {
//        return EventPriority.MONITOR;
//    }
//
//    @Override
//    public List<Recordable<? extends Function<?, ?>>> getRecordables(PlayerToggleSneakEvent event) {
//        Player player = event.getPlayer();
//
//        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
//        return List.of(RecEntitySneaking.of(entityId, event.isSneaking()));
//    }
//}