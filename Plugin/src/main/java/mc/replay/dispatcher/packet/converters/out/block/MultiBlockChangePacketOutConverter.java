package mc.replay.dispatcher.packet.converters.out.block;

import mc.replay.dispatcher.packet.converters.ReplayPacketOutConverter;
import mc.replay.nms.v1_16_5.recordable.block.RecMultiBlockChange;
import mc.replay.common.utils.reflection.JavaReflections;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class MultiBlockChangePacketOutConverter implements ReplayPacketOutConverter<RecMultiBlockChange> {

    @Override
    public @NotNull String packetClassName() {
        return "PacketPlayOutMultiBlockChange";
    }

    @Override
    public @Nullable RecMultiBlockChange recordableFromPacket(Object packet) {
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

            return RecMultiBlockChange.of(blockPosition, blockIndexes, blockData, flag);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}