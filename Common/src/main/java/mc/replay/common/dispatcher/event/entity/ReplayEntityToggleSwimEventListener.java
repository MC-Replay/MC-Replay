//package mc.replay.common.dispatcher.event.entity;
//
//import mc.replay.common.dispatcher.DispatcherEvent;
//import mc.replay.api.recording.recordables.Recordable;
//import mc.replay.api.recording.recordables.entity.EntityId;
//import mc.replay.nms.v1_16_R3.recordable.entity.action.RecEntitySwimming;
//import org.bukkit.entity.Entity;
//import org.bukkit.event.EventPriority;
//import org.bukkit.event.entity.EntityToggleSwimEvent;
//
//import java.util.List;
//import java.util.function.Function;
//
//public final class ReplayEntityToggleSwimEventListener implements DispatcherEvent<EntityToggleSwimEvent> {
//
//    @Override
//    public EventPriority getPriority() {
//        return EventPriority.MONITOR;
//    }
//
//    @Override
//    public List<Recordable<? extends Function<?, ?>>> getRecordables(EntityToggleSwimEvent event) {
//        Entity entity = event.getEntity();
//
//        EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());
//        return List.of(RecEntitySwimming.of(entityId, event.isSwimming()));
//    }
//}