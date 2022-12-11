//package mc.replay.common.recordables.entity.action;
//
//import mc.replay.api.recording.recordables.entity.EntityId;
//import mc.replay.common.recordables.RecordableEntity;
//import net.minecraft.server.v1_16_R3.DataWatcherRegistry;
//import net.minecraft.server.v1_16_R3.EntityPlayer;
//import net.minecraft.server.v1_16_R3.EntityPose;
//import net.minecraft.server.v1_16_R3.PacketPlayOutEntityMetadata;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.List;
//import java.util.function.Function;
//
//public record RecEntitySneaking(EntityId entityId, boolean sneaking) implements RecordableEntity {
//
//    public static RecEntitySneaking of(EntityId entityId, boolean sneaking) {
//        return new RecEntitySneaking(entityId, sneaking);
//    }
//
//    @Override
//    public @NotNull List<@NotNull Object> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
//        RecordableEntityData data = function.apply(this.entityId.entityId());
//
//        EntityPlayer entityPlayer = (EntityPlayer) data.entityPlayer();
//        entityPlayer.setFlag(1, this.sneaking);
//        entityPlayer.getDataWatcher().set(DataWatcherRegistry.s.a(6), this.sneaking ? EntityPose.CROUCHING : EntityPose.STANDING);
//
//        return List.of(new PacketPlayOutEntityMetadata(data.entityId(), entityPlayer.getDataWatcher(), true));
//    }
//}