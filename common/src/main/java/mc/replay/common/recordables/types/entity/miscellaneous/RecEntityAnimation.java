package mc.replay.common.recordables.types.entity.miscellaneous;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.packetlib.data.entity.EntityAnimation;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

public record RecEntityAnimation(EntityId entityId, EntityAnimation animation) implements Recordable {

    public RecEntityAnimation(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.readEnum(EntityAnimation.class)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.writeEnum(EntityAnimation.class, this.animation);
    }
}