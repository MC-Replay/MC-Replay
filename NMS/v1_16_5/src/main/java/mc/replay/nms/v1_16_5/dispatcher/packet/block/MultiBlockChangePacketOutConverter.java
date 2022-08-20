package mc.replay.nms.v1_16_5.dispatcher.packet.block;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.nms.v1_16_5.recordable.block.RecMultiBlockChange;
import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_16_R3.PacketPlayOutMultiBlockChange;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;

public class MultiBlockChangePacketOutConverter implements DispatcherPacketOut<PacketPlayOutMultiBlockChange> {

    @Override
    public @Nullable List<Recordable> getRecordable(Object packetClass) {
        PacketPlayOutMultiBlockChange packet = (PacketPlayOutMultiBlockChange) packetClass;

        try {
            Field positionField = packet.getClass().getDeclaredField("a");
            positionField.setAccessible(true);

            Object position = positionField.get(packet);
            int x = (int) JavaReflections.getMethod(position.getClass(), "getX").invoke(position);
            int y = (int) JavaReflections.getMethod(position.getClass(), "getY").invoke(position);
            int z = (int) JavaReflections.getMethod(position.getClass(), "getZ").invoke(position);

            Vector blockPosition = new Vector(x, y, z);
            short[] blockIndexes = JavaReflections.getField(packet.getClass(), "b", short[].class).get(packet);

            Field blockDataField = packet.getClass().getDeclaredField("c");
            blockDataField.setAccessible(true);

            Object[] blockDataObjectArray = (Object[]) blockDataField.get(packet);
            BlockData[] blockData = new BlockData[blockDataObjectArray.length];

            for (int i = 0; i < blockDataObjectArray.length; i++) {
                Object blockDataObject = blockDataObjectArray[i];
                blockData[i] = (BlockData) blockDataObject.getClass().getMethod("createCraftBlockData").invoke(blockDataObject);
            }

            boolean flag = JavaReflections.getField(packet.getClass(), "d", boolean.class).get(packet);

            return List.of(RecMultiBlockChange.of(blockPosition, blockIndexes, blockData, flag));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}