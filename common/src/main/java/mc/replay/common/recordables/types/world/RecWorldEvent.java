package mc.replay.common.recordables.types.world;

import mc.replay.api.recordables.Recordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecWorldEvent(int effectId, Vector position, int data,
                            boolean disableRelativeVolume) implements Recordable {

    public RecWorldEvent(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(INT),
                reader.read(BLOCK_POSITION),
                reader.read(INT),
                reader.read(BOOLEAN)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(INT, this.effectId);
        writer.write(BLOCK_POSITION, this.position);
        writer.write(INT, this.data);
        writer.write(BOOLEAN, this.disableRelativeVolume);
    }
}