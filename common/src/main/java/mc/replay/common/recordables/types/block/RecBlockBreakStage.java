package mc.replay.common.recordables.types.block;

import mc.replay.common.recordables.types.internal.BlockRelatedRecordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecBlockBreakStage(int entityId, Vector blockPosition, byte stage) implements BlockRelatedRecordable {

    public RecBlockBreakStage(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(VAR_INT),
                reader.read(BLOCK_POSITION),
                reader.read(BYTE)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(VAR_INT, this.entityId);
        writer.write(BLOCK_POSITION, this.blockPosition);
        writer.write(BYTE, this.stage);
    }

    @Override
    public Object identifier() {
        return this.blockPosition;
    }
}