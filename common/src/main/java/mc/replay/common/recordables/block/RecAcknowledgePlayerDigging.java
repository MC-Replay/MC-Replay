package mc.replay.common.recordables.block;

import mc.replay.common.recordables.interfaces.RecordableBlock;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.version.ClientboundAcknowledgePlayerDigging754_758Packet;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecAcknowledgePlayerDigging(Vector blockPosition, int blockStateId, int stateId,
                                          boolean successful) implements RecordableBlock {

    public RecAcknowledgePlayerDigging(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(BLOCK_POSITION),
                reader.read(INT),
                reader.read(INT),
                reader.read(BOOLEAN)
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

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(BLOCK_POSITION, this.blockPosition);
        writer.write(INT, this.blockStateId);
        writer.write(INT, this.stateId);
        writer.write(BOOLEAN, this.successful);
    }
}