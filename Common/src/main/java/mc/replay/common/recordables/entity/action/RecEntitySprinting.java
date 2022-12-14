//package mc.replay.common.recordables.entity.action;
//
//import mc.replay.api.recording.recordables.entity.EntityId;
//import mc.replay.common.recordables.RecordableEntity;
//import mc.replay.packetlib.data.entity.EntityMetadata;
//import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
//import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.List;
//import java.util.function.Function;
//
//public record RecEntitySprinting(EntityId entityId, boolean sprinting) implements RecordableEntity {
//
//    public static RecEntitySprinting of(EntityId entityId, boolean sprinting) {
//        return new RecEntitySprinting(entityId, sprinting);
//    }
//
//    @Override
//    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
//        RecordableEntityData data = function.apply(this.entityId.entityId());
//
//        EntityMetadata entityMetadata = new EntityMetadata();
//        entityMetadata.setSprinting(this.sprinting);
//
//        return List.of(new ClientboundEntityMetadataPacket(
//                data.entityId(),
//                entityMetadata
//        ));
//    }
//}