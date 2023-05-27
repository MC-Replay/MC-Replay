package mc.replay.common.recordables.types.entity.metadata.other;

import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.internal.EntityStateRecordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.BOOLEAN;

public record RecArmorStandBasePlate(EntityId entityId, boolean basePlate) implements EntityStateRecordable {

    public RecArmorStandBasePlate(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(BOOLEAN)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(BOOLEAN, this.basePlate);
    }
}