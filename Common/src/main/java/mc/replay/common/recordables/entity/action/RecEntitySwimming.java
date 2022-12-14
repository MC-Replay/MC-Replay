//package mc.replay.common.recordables.entity.action;
//
//import mc.replay.api.recording.recordables.entity.EntityId;
//import mc.replay.common.recordables.RecordableEntity;
//import mc.replay.packetlib.data.entity.EntityMetadata;
//import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
//import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
//import org.bukkit.entity.Pose;
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
//    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
//        RecordableEntityData data = function.apply(this.entityId.entityId());
//
//        EntityMetadata entityMetadata = new EntityMetadata();
//        entityMetadata.setSwimming(this.swimming);
//        entityMetadata.setPose((this.swimming) ? Pose.SWIMMING : Pose.STANDING);
//
//        return List.of(new ClientboundEntityMetadataPacket(
//                data.entityId(),
//                entityMetadata
//        ));
//    }
//}