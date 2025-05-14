package mc.replay.common.recordables.types.entity.miscellaneous;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

public record RecEntityAttach(EntityId attachedEntityId, EntityId holdingEntityId) implements Recordable {

    public RecEntityAttach(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                new EntityId(reader)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.attachedEntityId);
        writer.write(this.holdingEntityId);
    }
}