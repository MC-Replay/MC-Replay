package mc.replay.nms.v1_16_R3.dispatcher.packet.block;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.PacketConverter;
import mc.replay.nms.v1_16_R3.recordable.block.RecMultiBlockChange;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_16_R3.SectionPosition;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.bukkit.util.Vector;

import java.util.List;

public final class MultiBlockChangePacketOutConverter implements DispatcherPacketOut<PacketPlayOutMultiBlockChange> {

    @Override
    public List<Recordable> getRecordables(PacketPlayOutMultiBlockChange packet) {
        PacketConverter.ConvertedPacket convertedPacket = this.convert(packet);

        SectionPosition position = convertedPacket.get("a", SectionPosition.class);
        Vector sectionPosition = new Vector(
                position.getX(),
                position.getY(),
                position.getZ()
        );

        short[] blockIndexes = convertedPacket.get("b", short[].class);

        IBlockData[] iBlockDataArray = convertedPacket.get("c", IBlockData[].class);
        BlockData[] blockDataArray = new BlockData[iBlockDataArray.length];

        for (int i = 0; i < iBlockDataArray.length; i++) {
            blockDataArray[i] = CraftBlockData.fromData(iBlockDataArray[i]);
        }

        boolean flag = convertedPacket.get("d", Boolean.class);

        return List.of(RecMultiBlockChange.of(sectionPosition, blockIndexes, blockDataArray, flag));
    }
}