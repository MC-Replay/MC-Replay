package mc.replay.common.recordables.types.entity.movement;

import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.RecordableBufferTypes;
import mc.replay.common.recordables.types.internal.EntityMovementRecordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

public record RecEntityHeadRotation(EntityId entityId, float yaw) implements EntityMovementRecordable {

    public RecEntityHeadRotation(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(RecordableBufferTypes.ENTITY_ROTATION)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(RecordableBufferTypes.ENTITY_ROTATION, this.yaw);
    }
}