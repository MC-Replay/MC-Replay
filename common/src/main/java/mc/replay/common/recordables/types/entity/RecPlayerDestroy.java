package mc.replay.common.recordables.types.entity;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

public record RecPlayerDestroy(EntityId entityId) implements Recordable {

    public RecPlayerDestroy(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
    }
}