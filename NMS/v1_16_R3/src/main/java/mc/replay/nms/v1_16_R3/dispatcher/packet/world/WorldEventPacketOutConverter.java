package mc.replay.nms.v1_16_R3.dispatcher.packet.world;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.PacketConverter;
import mc.replay.nms.v1_16_R3.recordable.world.RecWorldEvent;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldEvent;
import org.bukkit.util.Vector;

import java.util.List;

public final class WorldEventPacketOutConverter implements DispatcherPacketOut<PacketPlayOutWorldEvent> {

    @Override
    public List<Recordable> getRecordables(PacketPlayOutWorldEvent packet) {
        PacketConverter.ConvertedPacket convertedPacket = this.convert(packet);

        int effectId = convertedPacket.get("a", Integer.class);

        BlockPosition position = convertedPacket.get("b", BlockPosition.class);
        Vector blockPosition = new Vector(
                position.getX(),
                position.getY(),
                position.getZ()
        );

        int data = convertedPacket.get("c", Integer.class);
        boolean disableRelativeVolume = convertedPacket.get("d", Boolean.class);

        return List.of(RecWorldEvent.of(effectId, blockPosition, data, disableRelativeVolume));
    }
}