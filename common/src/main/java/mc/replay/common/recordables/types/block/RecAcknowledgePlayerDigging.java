package mc.replay.common.recordables.types.block;

import mc.replay.common.recordables.types.internal.BlockRelatedRecordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecAcknowledgePlayerDigging(Vector blockPosition, int blockStateId, int stateId,
                                          boolean successful) implements BlockRelatedRecordable {

    public RecAcknowledgePlayerDigging(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(BLOCK_POSITION),
                reader.read(INT),
                reader.read(INT),
                reader.read(BOOLEAN)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(BLOCK_POSITION, this.blockPosition);
        writer.write(INT, this.blockStateId);
        writer.write(INT, this.stateId);
        writer.write(BOOLEAN, this.successful);
    }
}