package mc.replay.nms.entity;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import mc.replay.nms.MCReplayNMS_v1_16_R3;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.ReplayByteBuffer;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.entity.Entity;

import java.nio.ByteBuffer;
import java.util.*;

public final class DataWatcherReader_v1_16_R3 {

    private final MCReplayNMS_v1_16_R3 nms;

    public DataWatcherReader_v1_16_R3(MCReplayNMS_v1_16_R3 nms) {
        this.nms = nms;
    }

    public Map<Integer, Metadata.Entry<?>> readDataWatcher(Entity bukkitEntity) {
        Map<Integer, Metadata.Entry<?>> entries = new HashMap<>();

        net.minecraft.server.v1_16_R3.Entity entity = (net.minecraft.server.v1_16_R3.Entity) this.nms.getBukkitEntity(bukkitEntity);
        if (entity == null) return entries;

        DataWatcher dataWatcher = entity.getDataWatcher();
        if (dataWatcher == null) return entries;

        List<DataWatcher.Item<?>> items = dataWatcher.c();
        for (DataWatcher.Item<?> item : items == null ? new ArrayList<DataWatcher.Item<?>>() : items) {
            DataWatcherObject<?> object = item.a();

            DataWatcherSerializer<?> dataWatcherSerializer = object.b();
            int index = object.a();

            int type = DataWatcherRegistry.b(dataWatcherSerializer);
            Object value = item.b();

            ReplayByteBuffer.Type<Object> serializer = Metadata.getSerializer(type);
            if (serializer == null) continue;

            if (value instanceof Optional<?> optional && optional.isEmpty()) {
                value = null;
            } else if (type != Metadata.TYPE_BYTE && type != Metadata.TYPE_VAR_INT && type != Metadata.TYPE_FLOAT && type != Metadata.TYPE_STRING && type != Metadata.TYPE_BOOLEAN) {
                value = this.readSpecialValue(value, dataWatcherSerializer, serializer);

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
    private Object readSpecialValue(Object value, DataWatcherSerializer dataWatcherSerializer, ReplayByteBuffer.Type<?> serializer) {
        PacketDataSerializer packetDataSerializer = new PacketDataSerializer(Unpooled.buffer());
        packetDataSerializer.writerIndex(0);
        packetDataSerializer.readerIndex(0);

        dataWatcherSerializer.a(packetDataSerializer, value);

        ByteBuffer byteBuffer = ByteBuffer.wrap(packetDataSerializer.array());
        ReplayByteBuffer packetBuffer = new ReplayByteBuffer(byteBuffer);
        return packetBuffer.read(serializer);
    }
}