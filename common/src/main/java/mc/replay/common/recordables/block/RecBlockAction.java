package mc.replay.common.recordables.block;

import mc.replay.common.recordables.interfaces.RecordableBlock;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockActionPacket;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecBlockAction(Vector blockPosition, int blockId, byte actionId,
                             byte actionParam) implements RecordableBlock {

    public RecBlockAction(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(BLOCK_POSITION),
                reader.read(INT),
                reader.read(BYTE),
                reader.read(BYTE)
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of(new ClientboundBlockActionPacket(
                this.blockPosition,
                this.actionId,
                this.actionParam,
                this.blockId
        ));
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(BLOCK_POSITION, this.blockPosition);
        writer.write(INT, this.blockId);
        writer.write(BYTE, this.actionId);
        writer.write(BYTE, this.actionParam);
    }
}