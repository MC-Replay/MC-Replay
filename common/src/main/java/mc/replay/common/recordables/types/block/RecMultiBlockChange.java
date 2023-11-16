package mc.replay.common.recordables.types.block;

import mc.replay.common.recordables.types.internal.BlockRelatedRecordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public final class RecMultiBlockChange implements BlockRelatedRecordable {

    private final long chunkSectorPosition;
    private final boolean suppressLightUpdates;
    private final long[] blocks;

    private final long identifier;

    public RecMultiBlockChange(long chunkSectorPosition, boolean suppressLightUpdates, long[] blocks) {
        this.chunkSectorPosition = chunkSectorPosition;
        this.suppressLightUpdates = suppressLightUpdates;
        this.blocks = blocks;

        this.identifier = java.util.UUID.randomUUID().getMostSignificantBits();
    }

    public RecMultiBlockChange(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(LONG),
                reader.read(BOOLEAN),
                reader.read(LONG_ARRAY)
        );
    }

    public long chunkSectorPosition() {
        return this.chunkSectorPosition;
    }

    public boolean suppressLightUpdates() {
        return this.suppressLightUpdates;
    }

    public long[] blocks() {
        return this.blocks;
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(LONG, this.chunkSectorPosition);
        writer.write(BOOLEAN, this.suppressLightUpdates);
        writer.write(LONG_ARRAY, this.blocks);
    }

    @Override
    public Object identifier() {
        return this.identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecMultiBlockChange that = (RecMultiBlockChange) o;
        return chunkSectorPosition == that.chunkSectorPosition && suppressLightUpdates == that.suppressLightUpdates && Arrays.equals(blocks, that.blocks);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(chunkSectorPosition, suppressLightUpdates);
        result = 31 * result + Arrays.hashCode(blocks);
        return result;
    }
}