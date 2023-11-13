package mc.replay.nms.entity.metadata;

import com.github.steveice10.opennbt.tag.builtin.Tag;
import mc.replay.mappings.mapped.MappedEntityMetadataSerializerType;
import mc.replay.mappings.objects.EntityMetadataSerializerMapping;
import mc.replay.packetlib.data.Item;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.ReplayByteBuffer;
import net.kyori.adventure.text.Component;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Pose;
import org.bukkit.util.Vector;

import java.util.UUID;

public final class MetadataTypes {

    public static Metadata.Entry<Byte> Byte(byte value) {
        return createEntry(EntityMetadataSerializerMapping.Type.BYTE, value);
    }

    public static Metadata.Entry<Integer> VarInt(int value) {
        return createEntry(EntityMetadataSerializerMapping.Type.INT, value);
    }

    public static Metadata.Entry<Long> VarLong(long value) {
        return createEntry(EntityMetadataSerializerMapping.Type.LONG, value);
    }

    public static Metadata.Entry<Float> Float(float value) {
        return createEntry(EntityMetadataSerializerMapping.Type.FLOAT, value);
    }

    public static Metadata.Entry<String> String(String value) {
        return createEntry(EntityMetadataSerializerMapping.Type.STRING, value);
    }

    public static Metadata.Entry<Component> Chat(Component value) {
        return createEntry(EntityMetadataSerializerMapping.Type.COMPONENT, value);
    }

    public static Metadata.Entry<Component> OptChat(Component value) {
        return createEntry(EntityMetadataSerializerMapping.Type.OPTIONAL_COMPONENT, value);
    }

    public static Metadata.Entry<Item> Slot(Item value) {
        return createEntry(EntityMetadataSerializerMapping.Type.ITEM_STACK, value);
    }

    public static Metadata.Entry<Boolean> Boolean(boolean value) {
        return createEntry(EntityMetadataSerializerMapping.Type.BOOLEAN, value);
    }

    public static Metadata.Entry<Vector> Rotation(Vector value) {
        return createEntry(EntityMetadataSerializerMapping.Type.ROTATIONS, value);
    }

    public static Metadata.Entry<Vector> Position(Vector value) {
        return createEntry(EntityMetadataSerializerMapping.Type.BLOCK_POS, value);
    }

    public static Metadata.Entry<Vector> OptPosition(Vector value) {
        return createEntry(EntityMetadataSerializerMapping.Type.OPTIONAL_BLOCK_POS, value);
    }

    public static Metadata.Entry<BlockFace> Direction(BlockFace value) {
        return createEntry(EntityMetadataSerializerMapping.Type.DIRECTION, value);
    }

    public static Metadata.Entry<UUID> OptUUID(UUID value) {
        return createEntry(EntityMetadataSerializerMapping.Type.OPTIONAL_UUID, value);
    }

    public static Metadata.Entry<Integer> BlockState(int value) {
        return createEntry(EntityMetadataSerializerMapping.Type.BLOCK_STATE, value);
    }

    public static Metadata.Entry<Integer> OptBlockState(Integer value) {
        return createEntry(EntityMetadataSerializerMapping.Type.OPTIONAL_BLOCK_STATE, value);
    }

    public static Metadata.Entry<Tag> NBT(Tag value) {
        return createEntry(EntityMetadataSerializerMapping.Type.COMPOUND_TAG, value);
    }

    public static Metadata.Entry<Integer> Particle(int value) {
        return createEntry(EntityMetadataSerializerMapping.Type.PARTICLE, value);
    }

    public static Metadata.Entry<int[]> VillagerData(int villagerType,
                                                     int villagerProfession,
                                                     int level) {
        return createEntry(EntityMetadataSerializerMapping.Type.VILLAGER_DATA, new int[]{villagerType, villagerProfession, level});
    }

    public static Metadata.Entry<Integer> OptVarInt(Integer value) {
        return createEntry(EntityMetadataSerializerMapping.Type.OPTIONAL_UNSIGNED_INT, value);
    }

    public static Metadata.Entry<Pose> Pose(Pose value) {
        return createEntry(EntityMetadataSerializerMapping.Type.POSE, value);
    }

    public static Metadata.Entry<Integer> CatVariant(int value) {
        return createEntry(EntityMetadataSerializerMapping.Type.CAT_VARIANT, value);
    }

    public static Metadata.Entry<Integer> FrogVariant(int value) {
        return createEntry(EntityMetadataSerializerMapping.Type.FROG_VARIANT, value);
    }

    public static Metadata.Entry<Integer> PaintingVariant(int value) {
        return createEntry(EntityMetadataSerializerMapping.Type.PAINTING_VARIANT, value);
    }

    public static Metadata.Entry<Integer> SnifferState(int value) {
        return createEntry(EntityMetadataSerializerMapping.Type.SNIFFER_STATE, value);
    }

    public static Metadata.Entry<Byte> Vector3(byte value) {
        return createEntry(EntityMetadataSerializerMapping.Type.VECTOR3, value);
    }

    public static Metadata.Entry<float[]> Quaternion(float[] value) {
        return createEntry(EntityMetadataSerializerMapping.Type.QUATERNION, value);
    }

    @SuppressWarnings("unchecked")
    private static <T> Metadata.Entry<T> createEntry(EntityMetadataSerializerMapping.Type type, T value) {
        MappedEntityMetadataSerializerType mappedSerializerType = new MappedEntityMetadataSerializerType(type);
        ReplayByteBuffer.Type<T> serializer = (ReplayByteBuffer.Type<T>) mappedSerializerType.getSerializer();
        if (serializer == null) {
            throw new IllegalArgumentException("No serializer found for type " + type);
        }

        int typeId = mappedSerializerType.mapping().id();
        return new Metadata.Entry<>(typeId, value, serializer);
    }
}