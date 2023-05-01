package mc.replay.common.recordables.types.block;

import mc.replay.common.recordables.types.internal.BlockRelatedRecordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.BLOCK_POSITION;
import static mc.replay.packetlib.network.ReplayByteBuffer.INT;

public record RecBlockChange(Vector blockPosition, int blockStateId) implements BlockRelatedRecordable {

    public RecBlockChange(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(BLOCK_POSITION),
                reader.read(INT)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(BLOCK_POSITION, this.blockPosition);
        writer.write(INT, this.blockStateId);
    }
}