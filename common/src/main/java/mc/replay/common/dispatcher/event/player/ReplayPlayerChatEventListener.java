//package mc.replay.common.dispatcher.event.player;
//
//import mc.replay.common.dispatcher.DispatcherEvent;
//import mc.replay.api.recording.recordables.Recordable;
//import mc.replay.api.recording.recordables.entity.EntityId;
//import mc.replay.nms.global.recordable.RecPlayerChat;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventPriority;
//import org.bukkit.event.player.AsyncPlayerChatEvent;
//
//import java.util.List;
//import java.util.function.Function;
//
//public final class ReplayPlayerChatEventListener implements DispatcherEvent<AsyncPlayerChatEvent> {
//
//    @Override
//    public EventPriority getPriority() {
//        return EventPriority.MONITOR;
//    }
//
//    @Override
//    public List<Recordable<? extends Function<?, ?>>> getRecordables(AsyncPlayerChatEvent event) {
//        Player player = event.getPlayer();
//
//        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
//        return List.of(RecPlayerChat.of(entityId, player.getName(), event.getMessage()));
//    }
//}