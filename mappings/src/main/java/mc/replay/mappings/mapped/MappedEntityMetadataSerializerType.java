package mc.replay.mappings.mapped;

import mc.replay.mappings.MappingId;
import mc.replay.mappings.MappingKey;
import mc.replay.mappings.MappingsLoader;
import mc.replay.mappings.objects.EntityMetadataSerializerMapping;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.utils.ProtocolVersion;
import org.jetbrains.annotations.NotNull;

public final class MappedEntityMetadataSerializerType {

    private final EntityMetadataSerializerMapping.Type serializerType;
    private final EntityMetadataSerializerMapping mapping;

    public MappedEntityMetadataSerializerType(@NotNull ProtocolVersion protocolVersion, @NotNull EntityMetadataSerializerMapping.Type serializerType) {
        this.serializerType = serializerType;
        this.mapping = MappingsLoader.getEntityMetadataSerializerMappingsByKey(protocolVersion).get(MappingKey.from(serializerType.name()));
    }

    public MappedEntityMetadataSerializerType(@NotNull EntityMetadataSerializerMapping.Type serializerType) {
        this(ProtocolVersion.getServerVersion(), serializerType);
    }

    public MappedEntityMetadataSerializerType(@NotNull ProtocolVersion protocolVersion, int typeId) {
        this.mapping = MappingsLoader.getEntityMetadataSerializerMappingsById(protocolVersion).get(MappingId.from(typeId));
        this.serializerType = this.mapping == null ? null : this.mapping.type();
    }

    public MappedEntityMetadataSerializerType(int typeId) {
        this(ProtocolVersion.getServerVersion(), typeId);
    }

    public @NotNull EntityMetadataSerializerMapping.Type type() {
        return this.serializerType;
    }

    public @NotNull EntityMetadataSerializerMapping mapping() {
        return this.mapping;
    }

    public ReplayByteBuffer.Type<?> getSerializer() {
        if (this.serializerType == null) return null;

        return switch (this.serializerType) {
            case BYTE -> ReplayByteBuffer.BYTE;
            case INT -> ReplayByteBuffer.VAR_INT;
            case LONG -> ReplayByteBuffer.LONG;
            case FLOAT -> ReplayByteBuffer.FLOAT;
            case STRING -> ReplayByteBuffer.STRING;
            case COMPONENT -> ReplayByteBuffer.COMPONENT;
            case OPTIONAL_COMPONENT -> ReplayByteBuffer.OPT_CHAT;
            case ITEM_STACK -> ReplayByteBuffer.ITEM;
            case BOOLEAN -> ReplayByteBuffer.BOOLEAN;
            case ROTATIONS -> ReplayByteBuffer.ROTATION;
            case BLOCK_POS -> ReplayByteBuffer.BLOCK_POSITION;
            case OPTIONAL_BLOCK_POS -> ReplayByteBuffer.OPT_BLOCK_POSITION;
            case DIRECTION -> ReplayByteBuffer.BLOCK_FACE;
            case OPTIONAL_UUID -> ReplayByteBuffer.OPT_UUID;
            case BLOCK_STATE -> ReplayByteBuffer.VAR_INT;
            case OPTIONAL_BLOCK_STATE -> ReplayByteBuffer.OPT_BLOCK_ID;
            case COMPOUND_TAG -> ReplayByteBuffer.NBT;
            case VILLAGER_DATA -> ReplayByteBuffer.VILLAGER_DATA;
            case OPTIONAL_UNSIGNED_INT -> ReplayByteBuffer.OPT_VAR_INT;
            case POSE -> ReplayByteBuffer.POSE;
            case CAT_VARIANT -> ReplayByteBuffer.VAR_INT;
            case FROG_VARIANT -> ReplayByteBuffer.VAR_INT;
            case PAINTING_VARIANT -> ReplayByteBuffer.VAR_INT;
            case SNIFFER_STATE -> ReplayByteBuffer.VAR_INT;
            case VECTOR3 -> ReplayByteBuffer.ROTATION;
            case QUATERNION -> QUATERNION;
            default -> null;
        };
    }

    private static final ReplayByteBuffer.Type<float[]> QUATERNION = new ReplayByteBuffer.Type<>() {
        @Override
        public ReplayByteBuffer.@NotNull TypeWriter<float[]> writer() {
            return (buffer, value) -> {
                buffer.write(ReplayByteBuffer.FLOAT, value[0]);
                buffer.write(ReplayByteBuffer.FLOAT, value[1]);
                buffer.write(ReplayByteBuffer.FLOAT, value[2]);
                buffer.write(ReplayByteBuffer.FLOAT, value[3]);
                return -1;
            };
        }

        @Override
        public ReplayByteBuffer.@NotNull TypeReader<float[]> reader() {
            return buffer -> {
                final float x = buffer.read(ReplayByteBuffer.FLOAT);
                final float y = buffer.read(ReplayByteBuffer.FLOAT);
                final float z = buffer.read(ReplayByteBuffer.FLOAT);
                final float w = buffer.read(ReplayByteBuffer.FLOAT);
                return new float[]{x, y, z, w};
            };
        }
    };
}