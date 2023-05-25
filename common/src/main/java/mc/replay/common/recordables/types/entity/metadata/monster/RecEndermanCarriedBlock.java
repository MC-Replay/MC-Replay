package mc.replay.common.recordables.types.entity.metadata.monster;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.VAR_INT;

public record RecEndermanCarriedBlock(EntityId entityId, Integer blockId) implements Recordable {

    public RecEndermanCarriedBlock(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.readOptional(VAR_INT)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.writeOptional(VAR_INT, this.blockId);
    }
}