package mc.replay.common.recordables.types.entity.metadata.animal.tameable;

import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.internal.EntityStateRecordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.VAR_INT;

public record RecWolfCollarColor(EntityId entityId, int collarColor) implements EntityStateRecordable {

    public RecWolfCollarColor(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(VAR_INT)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(VAR_INT, this.collarColor);
    }
}