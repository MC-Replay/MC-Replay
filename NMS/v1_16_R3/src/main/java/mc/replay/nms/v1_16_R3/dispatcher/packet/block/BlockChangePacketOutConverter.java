package mc.replay.nms.v1_16_R3.dispatcher.packet.block;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.nms.v1_16_R3.recordable.block.RecBlockChange;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockChange;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;

public final class BlockChangePacketOutConverter implements DispatcherPacketOut<PacketPlayOutBlockChange> {

    @Override
    public @Nullable List<Recordable> getRecordables(PacketPlayOutBlockChange packet) {
        try {
            Field positionField = packet.getClass().getDeclaredField("a");
            positionField.setAccessible(true);

            BlockPosition position = (BlockPosition) positionField.get(packet);
            Vector blockPosition = new Vector(
                    position.getX(),
                    position.getY(),
                    position.getZ()
            );

            IBlockData blockData = packet.block;
            CraftBlockData craftBlockData = CraftBlockData.fromData(blockData);

            return List.of(RecBlockChange.of(blockPosition, craftBlockData));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}