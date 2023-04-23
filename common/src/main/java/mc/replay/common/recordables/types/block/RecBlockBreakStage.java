package mc.replay.common.recordables.types.block;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.BLOCK_POSITION;
import static mc.replay.packetlib.network.ReplayByteBuffer.BYTE;

public record RecBlockBreakStage(Vector blockPosition, byte stage) implements Recordable {

    public RecBlockBreakStage(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(BLOCK_POSITION),
                reader.read(BYTE)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(BLOCK_POSITION, this.blockPosition);
        writer.write(BYTE, this.stage);
    }
}