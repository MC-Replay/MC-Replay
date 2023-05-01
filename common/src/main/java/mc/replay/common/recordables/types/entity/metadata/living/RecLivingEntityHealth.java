package mc.replay.common.recordables.types.entity.metadata.living;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.FLOAT;

public record RecLivingEntityHealth(EntityId entityId, float health) implements Recordable {

    public RecLivingEntityHealth(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(FLOAT)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(FLOAT, this.health);
    }
}