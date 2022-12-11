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
//public record RecEntitySwimming(EntityId entityId, boolean swimming) implements RecordableEntity {
//
//    public static RecEntitySwimming of(EntityId entityId, boolean swimming) {
//        return new RecEntitySwimming(entityId, swimming);
//    }
//
//    @Override
//    public @NotNull List<@NotNull Object> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
//        RecordableEntityData data = function.apply(this.entityId.entityId());
//
//        EntityPlayer entityPlayer = (EntityPlayer) data.entityPlayer();
//        entityPlayer.setFlag(4, this.swimming);
//        entityPlayer.getDataWatcher().set(DataWatcherRegistry.s.a(6), this.swimming ? EntityPose.SWIMMING : EntityPose.STANDING);
//
//        return List.of(new PacketPlayOutEntityMetadata(data.entityId(), entityPlayer.getDataWatcher(), true));
//    }
//}