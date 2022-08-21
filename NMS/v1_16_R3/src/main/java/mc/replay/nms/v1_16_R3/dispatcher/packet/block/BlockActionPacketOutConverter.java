package mc.replay.nms.v1_16_R3.dispatcher.packet.block;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.PacketConverter;
import mc.replay.nms.v1_16_R3.recordable.block.RecBlockAction;
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockAction;
import org.bukkit.util.Vector;

import java.util.List;

public final class BlockActionPacketOutConverter implements DispatcherPacketOut<PacketPlayOutBlockAction> {

    @Override
    public List<Recordable> getRecordables(PacketPlayOutBlockAction packet) {
        PacketConverter.ConvertedPacket convertedPacket = this.convert(packet);

        BlockPosition position = convertedPacket.get("a", BlockPosition.class);
        Vector blockPosition = new Vector(
                position.getX(),
                position.getY(),
                position.getZ()
        );

        int actionId = convertedPacket.get("b", Integer.class);
        int actionParam = convertedPacket.get("c", Integer.class);

        Block block = convertedPacket.get("d", Block.class);
        int blockId = IRegistry.BLOCK.a(block);

        return List.of(RecBlockAction.of(blockPosition, blockId, actionId, actionParam));
    }
}