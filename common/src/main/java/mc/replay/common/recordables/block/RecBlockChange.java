package mc.replay.common.recordables.block;

import mc.replay.common.recordables.interfaces.RecordableBlock;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockChangePacket;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

import static mc.replay.packetlib.network.ReplayByteBuffer.BLOCK_POSITION;
import static mc.replay.packetlib.network.ReplayByteBuffer.INT;

public record RecBlockChange(Vector blockPosition, int blockStateId) implements RecordableBlock {

    public RecBlockChange(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(BLOCK_POSITION),
                reader.read(INT)
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of(new ClientboundBlockChangePacket(
                this.blockPosition,
                this.blockStateId
        ));
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(BLOCK_POSITION, this.blockPosition);
        writer.write(INT, this.blockStateId);
    }
}