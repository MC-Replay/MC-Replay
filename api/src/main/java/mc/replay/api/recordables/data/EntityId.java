package mc.replay.api.recordables.data;

import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static mc.replay.packetlib.network.ReplayByteBuffer.INT;
import static mc.replay.packetlib.network.ReplayByteBuffer.UUID;

public record EntityId(UUID entityUuid, int entityId) implements ReplayByteBuffer.Writer {

    public EntityId(@NotNull ReplayByteBuffer reader) {
        this(
                reader.readOptional(UUID),
                reader.read(INT)
        );
    }

    public static EntityId of(UUID entityUuid, int entityId) {
        return new EntityId(entityUuid, entityId);
    }

    public static EntityId of(int entityId) {
        return new EntityId(null, entityId);
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.writeOptional(UUID, this.entityUuid);
        writer.write(INT, this.entityId);
    }
}