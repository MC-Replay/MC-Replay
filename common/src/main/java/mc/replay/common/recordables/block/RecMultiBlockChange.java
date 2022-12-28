package mc.replay.common.recordables.block;

import mc.replay.common.recordables.interfaces.RecordableBlock;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundMultiBlockChangePacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecMultiBlockChange(long chunkSectorPosition, boolean suppressLightUpdates,
                                  long[] blocks) implements RecordableBlock {

    public RecMultiBlockChange(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(LONG),
                reader.read(BOOLEAN),
                reader.read(LONG_ARRAY)
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of(new ClientboundMultiBlockChangePacket(
                this.chunkSectorPosition,
                this.suppressLightUpdates,
                this.blocks
        ));
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(LONG, this.chunkSectorPosition);
        writer.write(BOOLEAN, this.suppressLightUpdates);
        writer.write(LONG_ARRAY, this.blocks);
    }
}