package mc.replay.common.recordables.types.entity.miscellaneous;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static mc.replay.packetlib.network.ReplayByteBuffer.BYTE;
import static mc.replay.packetlib.network.ReplayByteBuffer.VAR_INT;

public record RecEntityMetadataChange(EntityId entityId,
                                      Map<Integer, Metadata.Entry<?>> metadata) implements Recordable {

    public RecEntityMetadataChange(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                readMetadata(reader)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);

        for (Map.Entry<Integer, Metadata.Entry<?>> entry : this.metadata.entrySet()) {
            writer.write(BYTE, entry.getKey().byteValue());
            writer.write(entry.getValue());
        }
        writer.write(BYTE, (byte) 0xFF); // End
    }

    private static Map<Integer, Metadata.Entry<?>> readMetadata(@NotNull ReplayByteBuffer reader) {
        Map<Integer, Metadata.Entry<?>> entries = new HashMap<>();
        byte index;
        while ((index = reader.read(BYTE)) != (byte) 0xFF) {
            final int type = reader.read(VAR_INT);
            entries.put((int) index, Metadata.Entry.read(type, reader));
        }
        return entries;
    }
}