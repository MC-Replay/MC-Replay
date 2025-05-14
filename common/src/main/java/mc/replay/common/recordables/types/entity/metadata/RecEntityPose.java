package mc.replay.common.recordables.types.entity.metadata;

import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.internal.EntityStateRecordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.bukkit.entity.Pose;
import org.jetbrains.annotations.NotNull;

public record RecEntityPose(EntityId entityId, Pose pose) implements EntityStateRecordable {

    public RecEntityPose(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.readEnum(Pose.class)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.writeEnum(Pose.class, this.pose);
    }
}