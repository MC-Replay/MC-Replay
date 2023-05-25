package mc.replay.common.recordables.types.entity.metadata;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.VAR_INT;

public record RecEntityVariant(EntityId entityId, int variant) implements Recordable {

    public RecEntityVariant(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(VAR_INT)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(VAR_INT, this.variant);
    }
}