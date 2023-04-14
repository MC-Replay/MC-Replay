package mc.replay.common.recordables.types.entity;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record RecEntityDestroy(List<EntityId> entityIds) implements Recordable {

    public RecEntityDestroy(@NotNull ReplayByteBuffer reader) {
        this(
                reader.readCollection(EntityId::new)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.writeCollection(this.entityIds);
    }
}