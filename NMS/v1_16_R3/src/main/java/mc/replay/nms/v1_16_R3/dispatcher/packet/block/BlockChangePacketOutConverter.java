package mc.replay.nms.v1_16_R3.dispatcher.packet.block;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.PacketConverter;
import mc.replay.nms.v1_16_R3.recordable.block.RecBlockChange;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockChange;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.bukkit.util.Vector;

import java.util.List;

public final class BlockChangePacketOutConverter implements DispatcherPacketOut<PacketPlayOutBlockChange> {

    @Override
    public List<Recordable> getRecordables(PacketPlayOutBlockChange packet) {
        PacketConverter.ConvertedPacket convertedPacket = this.convert(packet);

        BlockPosition position = convertedPacket.get("a", BlockPosition.class);
        Vector blockPosition = new Vector(
                position.getX(),
                position.getY(),
                position.getZ()
        );

        IBlockData iBlockData = convertedPacket.get("block", IBlockData.class);
        CraftBlockData craftBlockData = CraftBlockData.fromData(iBlockData);

        return List.of(RecBlockChange.of(blockPosition, craftBlockData));
    }
}