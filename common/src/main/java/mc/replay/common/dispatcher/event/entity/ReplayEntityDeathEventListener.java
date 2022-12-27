//package mc.replay.common.dispatcher.event.entity;
//
//import mc.replay.api.recording.recordables.Recordable;
//import mc.replay.api.recording.recordables.entity.EntityId;
//import mc.replay.common.dispatcher.DispatcherEvent;
//import mc.replay.common.recordables.entity.RecEntityDestroy;
//import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
//import org.bukkit.entity.Entity;
//import org.bukkit.event.EventPriority;
//import org.bukkit.event.entity.EntityDeathEvent;
//
//import java.util.List;
//import java.util.function.Function;
//
//public final class ReplayEntityDeathEventListener implements DispatcherEvent<EntityDeathEvent> {
//
//    @Override
//    public EventPriority getPriority() {
//        return EventPriority.MONITOR;
//    }
//
//    @Override
//    public List<Recordable<? extends Function<?, ?>>> getRecordables(EntityDeathEvent event) {
//        Entity entity = event.getEntity();
//        net.minecraft.server.v1_16_R3.Entity craftEntity = ((CraftEntity) entity).getHandle();
//        //        if (entity instanceof Player || entity instanceof NPC || craftEntity instanceof RecordingFakePlayerImpl)
//        //            return null;
//
//        // TODO check if entity is replay entity
//
//        EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());
//        return List.of(RecEntityDestroy.of(entityId));
//    }
//}