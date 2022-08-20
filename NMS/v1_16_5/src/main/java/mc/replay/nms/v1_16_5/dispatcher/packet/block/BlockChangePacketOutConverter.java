package mc.replay.nms.v1_16_5.dispatcher.packet.block;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.nms.v1_16_5.recordable.block.RecBlockChange;
import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockChange;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;

public class BlockChangePacketOutConverter implements DispatcherPacketOut<PacketPlayOutBlockChange> {

    @Override
    public @Nullable List<Recordable> getRecordable(Object packetClass) {
        PacketPlayOutBlockChange packet = (PacketPlayOutBlockChange) packetClass;

        try {
            Field positionField = packet.getClass().getDeclaredField("a");
            positionField.setAccessible(true);

            Object position = positionField.get(packet);
            int x = (int) JavaReflections.getMethod(position.getClass(), "getX").invoke(position);
            int y = (int) JavaReflections.getMethod(position.getClass(), "getY").invoke(position);
            int z = (int) JavaReflections.getMethod(position.getClass(), "getZ").invoke(position);

            Vector blockPosition = new Vector(x, y, z);

            Object blockDataObject = packet.getClass().getField("block").get(packet);
            BlockData craftBlockData = (BlockData) blockDataObject.getClass().getMethod("createCraftBlockData").invoke(blockDataObject);

            return List.of(RecBlockChange.of(blockPosition, craftBlockData));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}