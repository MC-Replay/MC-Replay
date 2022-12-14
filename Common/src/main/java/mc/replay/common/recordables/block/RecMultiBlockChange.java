package mc.replay.common.recordables.block;

import mc.replay.common.recordables.RecordableBlock;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundMultiBlockChangePacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecMultiBlockChange(long chunkSectorPosition, boolean suppressLightUpdates,
                                  long[] blocks) implements RecordableBlock {

    public static RecMultiBlockChange of(long chunkSectorPosition, boolean suppressLightUpdates, long[] blocks) {
        return new RecMultiBlockChange(
                chunkSectorPosition,
                suppressLightUpdates,
                blocks
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
}