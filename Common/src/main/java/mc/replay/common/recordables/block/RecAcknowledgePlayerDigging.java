package mc.replay.common.recordables.block;

import mc.replay.common.recordables.RecordableBlock;
import mc.replay.packetlib.network.packet.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.version.ClientboundAcknowledgePlayerDigging754_758Packet;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecAcknowledgePlayerDigging(Vector blockPosition, int blockStateId, int stateId,
                                          boolean successful) implements RecordableBlock {

    public static RecAcknowledgePlayerDigging of(Vector blockPosition, int blockStateId, int stateId, boolean successful) {
        return new RecAcknowledgePlayerDigging(
                blockPosition,
                blockStateId,
                stateId,
                successful
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of(new ClientboundAcknowledgePlayerDigging754_758Packet(
                this.blockPosition,
                this.blockStateId,
                this.stateId,
                this.successful
        ));
    }
}