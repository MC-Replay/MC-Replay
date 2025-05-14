package mc.replay.common.recordables.types.entity.miscellaneous;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.BYTE;

public record RecEntityStatus(EntityId entityId, byte status) implements Recordable {

    public RecEntityStatus(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(BYTE)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(BYTE, this.status);
    }
}