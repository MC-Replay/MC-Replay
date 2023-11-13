package mc.replay.nms.entity;

import io.netty.buffer.Unpooled;
import mc.replay.mappings.mapped.MappedEntityMetadataSerializerType;
import mc.replay.mappings.objects.EntityMetadataSerializerMapping;
import mc.replay.nms.MCReplayNMS_v1_17_R1;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.ReplayByteBuffer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import org.bukkit.entity.Entity;

import java.nio.ByteBuffer;
import java.util.*;

import static mc.replay.mappings.objects.EntityMetadataSerializerMapping.Type.*;

public final class DataWatcherReader_v1_17_R1 {

    private final MCReplayNMS_v1_17_R1 nms;

    public DataWatcherReader_v1_17_R1(MCReplayNMS_v1_17_R1 nms) {
        this.nms = nms;
    }

    @SuppressWarnings("unchecked")
    public Map<Integer, Metadata.Entry<?>> readDataWatcher(Entity bukkitEntity) {
        Map<Integer, Metadata.Entry<?>> entries = new HashMap<>();

        net.minecraft.world.entity.Entity entity = (net.minecraft.world.entity.Entity) this.nms.getNMSEntity(bukkitEntity);
        if (entity == null) return entries;

        SynchedEntityData entityData = entity.getEntityData();
        if (entityData == null) return entries;

        List<SynchedEntityData.DataItem<?>> items = entityData.getAll();
        for (SynchedEntityData.DataItem<?> item : items == null ? new ArrayList<SynchedEntityData.DataItem<?>>() : items) {
            EntityDataAccessor<?> accessor = item.getAccessor();

            EntityDataSerializer<?> entityDataSerializer = accessor.getSerializer();
            int index = accessor.getId();

            int type = EntityDataSerializers.getSerializedId(entityDataSerializer);
            Object value = item.getValue();

            MappedEntityMetadataSerializerType mappedSerializer = new MappedEntityMetadataSerializerType(type);
            EntityMetadataSerializerMapping.Type serializerType = mappedSerializer.type();
            ReplayByteBuffer.Type<Object> serializer = (ReplayByteBuffer.Type<Object>) mappedSerializer.getSerializer();
            if (serializer == null) continue;

            if (value instanceof Optional<?> optional && optional.isEmpty()) {
                value = null;
            } else if (serializerType != BYTE && serializerType != INT && serializerType != FLOAT && serializerType != STRING && serializerType != BOOLEAN) {
                value = this.readSpecialValue(value, entityDataSerializer, serializer);

                if (value == null) {
                    continue;
                }
            }

            Metadata.Entry<Object> entry = new Metadata.Entry<>(type, value, serializer);
            entries.put(index, entry);
        }

        return entries;
    }

    @SuppressWarnings("unchecked, rawtypes")
    private Object readSpecialValue(Object value, EntityDataSerializer dataWatcherSerializer, ReplayByteBuffer.Type<?> serializer) {
        FriendlyByteBuf friendlyByteBuf = new FriendlyByteBuf(Unpooled.buffer());
        friendlyByteBuf.writerIndex(0);
        friendlyByteBuf.readerIndex(0);

        dataWatcherSerializer.write(friendlyByteBuf, value);

        ByteBuffer byteBuffer = ByteBuffer.wrap(friendlyByteBuf.array());
        ReplayByteBuffer packetBuffer = new ReplayByteBuffer(byteBuffer);
        return packetBuffer.read(serializer);
    }
}