package mc.replay.common.recordables.types.block;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import mc.replay.api.recordables.Recordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecBlockEntityData(Vector blockPosition, int action, CompoundTag data) implements Recordable {

    public RecBlockEntityData(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(BLOCK_POSITION),
                reader.read(INT),
                (CompoundTag) reader.read(NBT)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(BLOCK_POSITION, this.blockPosition);
        writer.write(INT, this.action);
        writer.write(NBT, this.data);
    }
}