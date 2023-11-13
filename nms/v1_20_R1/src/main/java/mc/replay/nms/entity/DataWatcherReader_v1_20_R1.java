package mc.replay.nms.entity;

import io.netty.buffer.Unpooled;
import mc.replay.nms.MCReplayNMS_v1_20_R1;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.ReplayByteBuffer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import org.bukkit.entity.Entity;

import java.nio.ByteBuffer;
import java.util.*;

public final class DataWatcherReader_v1_20_R1 {

    private final MCReplayNMS_v1_20_R1 nms;

    public DataWatcherReader_v1_20_R1(MCReplayNMS_v1_20_R1 nms) {
        this.nms = nms;
    }

    public Map<Integer, Metadata.Entry<?>> readDataWatcher(Entity bukkitEntity) {
        Map<Integer, Metadata.Entry<?>> entries = new HashMap<>();

        net.minecraft.world.entity.Entity entity = (net.minecraft.world.entity.Entity) this.nms.getNMSEntity(bukkitEntity);
        if (entity == null) return entries;

        SynchedEntityData entityData = entity.getEntityData();
        if (entityData == null) return entries;

        List<SynchedEntityData.DataValue<?>> items = entityData.getNonDefaultValues();
        for (SynchedEntityData.DataValue<?> item : items == null ? new ArrayList<SynchedEntityData.DataValue<?>>() : items) {
            EntityDataSerializer<?> entityDataSerializer = item.serializer();
            int index = item.id();

            int type = EntityDataSerializers.getSerializedId(entityDataSerializer);
            Object value = item.value();

            ReplayByteBuffer.Type<Object> serializer = Metadata.getSerializer(type);
            if (serializer == null) continue;

            if (value instanceof Optional<?> optional && optional.isEmpty()) {
                value = null;
            } else if (type != Metadata.TYPE_BYTE && type != Metadata.TYPE_VAR_INT && type != Metadata.TYPE_FLOAT && type != Metadata.TYPE_STRING && type != Metadata.TYPE_BOOLEAN) {
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