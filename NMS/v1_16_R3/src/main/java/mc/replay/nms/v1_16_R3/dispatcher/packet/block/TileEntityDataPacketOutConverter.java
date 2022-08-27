package mc.replay.nms.v1_16_R3.dispatcher.packet.block;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.utils.PacketConverter;
import mc.replay.nms.v1_16_R3.recordable.block.RecTileEntityData;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.PacketPlayOutTileEntityData;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.function.Function;

public final class TileEntityDataPacketOutConverter implements DispatcherPacketOut<PacketPlayOutTileEntityData> {

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(PacketPlayOutTileEntityData packet) {
        PacketConverter.ConvertedPacket convertedPacket = this.convert(packet);

        BlockPosition position = convertedPacket.get("a", BlockPosition.class);
        Vector blockPosition = new Vector(
                position.getX(),
                position.getY(),
                position.getZ()
        );

        int action = convertedPacket.get("b", Integer.class);
        NBTTagCompound nbtTagCompound = convertedPacket.get("c", NBTTagCompound.class);

        return List.of(RecTileEntityData.of(blockPosition, action, nbtTagCompound));
    }
}