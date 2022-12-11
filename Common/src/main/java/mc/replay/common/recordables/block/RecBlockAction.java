package mc.replay.common.recordables.block;

import mc.replay.common.recordables.RecordableBlock;
import mc.replay.packetlib.network.packet.ClientboundPacket;
import mc.replay.packetlib.network.packet.ServerboundPacket;
import mc.replay.packetlib.network.packet.clientbound.ClientboundBlockActionPacket;
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockAction;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecBlockAction(Vector blockPosition, int blockId, byte actionId,
                             byte actionParam) implements RecordableBlock {

    public static RecBlockAction of(Vector blockPosition, int blockId, byte actionId, byte actionParam) {
        return new RecBlockAction(
                blockPosition,
                blockId,
                actionId,
                actionParam
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
}