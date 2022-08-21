package mc.replay.nms.v1_16_R3.dispatcher.packet.block;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.nms.v1_16_R3.recordable.block.RecMultiBlockChange;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.PacketPlayOutMultiBlockChange;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;

public final class MultiBlockChangePacketOutConverter implements DispatcherPacketOut<PacketPlayOutMultiBlockChange> {

    @Override
    public @Nullable List<Recordable> getRecordables(PacketPlayOutMultiBlockChange packet) {
        try {
            Field positionField = packet.getClass().getDeclaredField("a");
            positionField.setAccessible(true);

            BlockPosition position = (BlockPosition) positionField.get(packet);
            Vector blockPosition = new Vector(
                    position.getX(),
                    position.getY(),
                    position.getZ()
            );

            short[] blockIndexes = JavaReflections.getField(packet.getClass(), "b", short[].class).get(packet);

            Field blockDataField = packet.getClass().getDeclaredField("c");
            blockDataField.setAccessible(true);

            IBlockData[] blockDataObjectArray = (IBlockData[]) blockDataField.get(packet);
            BlockData[] blockData = new BlockData[blockDataObjectArray.length];

            for (int i = 0; i < blockDataObjectArray.length; i++) {
                IBlockData blockDataObject = blockDataObjectArray[i];
                blockData[i] = CraftBlockData.fromData(blockDataObject);
            }

            boolean flag = JavaReflections.getField(packet.getClass(), "d", boolean.class).get(packet);

            return List.of(RecMultiBlockChange.of(blockPosition, blockIndexes, blockData, flag));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}