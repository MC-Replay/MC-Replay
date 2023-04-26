package mc.replay.common.recordables.types.block;

import mc.replay.api.recordables.Recordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecMultiBlockChange(long chunkSectorPosition, boolean suppressLightUpdates,
                                  long[] blocks) implements Recordable {

    public RecMultiBlockChange(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(LONG),
                reader.read(BOOLEAN),
                reader.read(LONG_ARRAY)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(LONG, this.chunkSectorPosition);
        writer.write(BOOLEAN, this.suppressLightUpdates);
        writer.write(LONG_ARRAY, this.blocks);
    }
}