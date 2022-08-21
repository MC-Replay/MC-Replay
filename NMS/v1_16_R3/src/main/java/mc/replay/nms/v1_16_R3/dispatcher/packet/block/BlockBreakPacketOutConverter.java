package mc.replay.nms.v1_16_R3.dispatcher.packet.block;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.nms.v1_16_R3.recordable.block.RecBlockBreak;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockBreak;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;

public final class BlockBreakPacketOutConverter implements DispatcherPacketOut<PacketPlayOutBlockBreak> {

    @Override
    public @Nullable List<Recordable> getRecordables(PacketPlayOutBlockBreak packet) {
        try {
            Field positionField = packet.getClass().getDeclaredField("c");
            positionField.setAccessible(true);

            BlockPosition position = (BlockPosition) positionField.get(packet);
            Vector blockPosition = new Vector(
                    position.getX(),
                    position.getY(),
                    position.getZ()
            );

            Field blockDataField = packet.getClass().getDeclaredField("d");
            blockDataField.setAccessible(true);

            IBlockData blockDataObject = (IBlockData) blockDataField.get(packet);
            CraftBlockData craftBlockData = CraftBlockData.fromData(blockDataObject);

            Field digTypeField = packet.getClass().getDeclaredField("a");
            digTypeField.setAccessible(true);

            PacketPlayInBlockDig.EnumPlayerDigType digType = (PacketPlayInBlockDig.EnumPlayerDigType) digTypeField.get(packet);
            boolean instaBreak = JavaReflections.getField(packet.getClass(), "e", boolean.class).get(packet);

            return List.of(RecBlockBreak.of(blockPosition, craftBlockData, digType.name(), instaBreak));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}