package mc.replay.nms.v1_16_R3.dispatcher.packet.block;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.utils.PacketConverter;
import mc.replay.nms.v1_16_R3.recordable.block.RecBlockBreak;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockBreak;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.bukkit.util.Vector;

import java.util.List;

public final class BlockBreakPacketOutConverter implements DispatcherPacketOut<PacketPlayOutBlockBreak> {

    @Override
    public List<Recordable> getRecordables(PacketPlayOutBlockBreak packet) {
        PacketConverter.ConvertedPacket convertedPacket = this.convert(packet);

        BlockPosition position = convertedPacket.get("c", BlockPosition.class);
        Vector blockPosition = new Vector(
                position.getX(),
                position.getY(),
                position.getZ()
        );

        IBlockData iBlockData = convertedPacket.get("d", IBlockData.class);
        CraftBlockData craftBlockData = CraftBlockData.fromData(iBlockData);

        PacketPlayInBlockDig.EnumPlayerDigType digType = convertedPacket.get("a", PacketPlayInBlockDig.EnumPlayerDigType.class);
        boolean instaBreak = convertedPacket.get("e", Boolean.class);

        return List.of(RecBlockBreak.of(blockPosition, craftBlockData, digType.name(), instaBreak));
    }
}