package mc.replay.common.recordables.block;

import mc.replay.common.recordables.RecordableBlock;
import mc.replay.packetlib.network.packet.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.ClientboundBlockChangePacket;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecBlockChange(Vector blockPosition, int blockStateId) implements RecordableBlock {

    public static RecBlockChange of(Vector blockPosition, int blockStateId) {
        return new RecBlockChange(
                blockPosition,
                blockStateId
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of(new ClientboundBlockChangePacket(
                this.blockPosition,
                this.blockStateId
        ));
    }
}