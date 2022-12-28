package mc.replay.common.recordables.block;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import mc.replay.common.recordables.interfaces.RecordableBlock;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockEntityDataPacket;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecBlockEntityData(Vector blockPosition, int action, CompoundTag data) implements RecordableBlock {

    public RecBlockEntityData(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(BLOCK_POSITION),
                reader.read(INT),
                (CompoundTag) reader.read(NBT)
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of(new ClientboundBlockEntityDataPacket(
                this.blockPosition,
                this.action,
                this.data
        ));
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(BLOCK_POSITION, this.blockPosition);
        writer.write(INT, this.action);
        writer.write(NBT, this.data);
    }
}