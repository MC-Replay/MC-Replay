package mc.replay.common.recordables.types.entity.metadata.other;

import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.internal.EntityStateRecordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.bukkit.Rotation;
import org.jetbrains.annotations.NotNull;

public record RecItemFrameRotation(EntityId entityId, Rotation rotation) implements EntityStateRecordable {

    public RecItemFrameRotation(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.readEnum(Rotation.class)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.writeEnum(Rotation.class, this.rotation);
    }
}