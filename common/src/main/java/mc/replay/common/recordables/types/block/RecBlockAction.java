package mc.replay.common.recordables.types.block;

import mc.replay.api.recordables.Recordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecBlockAction(Vector blockPosition, int blockId, byte actionId,
                             byte actionParam) implements Recordable {

    public RecBlockAction(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(BLOCK_POSITION),
                reader.read(INT),
                reader.read(BYTE),
                reader.read(BYTE)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(BLOCK_POSITION, this.blockPosition);
        writer.write(INT, this.blockId);
        writer.write(BYTE, this.actionId);
        writer.write(BYTE, this.actionParam);
    }
}