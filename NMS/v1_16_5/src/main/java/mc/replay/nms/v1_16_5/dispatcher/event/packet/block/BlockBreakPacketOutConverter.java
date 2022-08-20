package mc.replay.nms.v1_16_5.dispatcher.event.packet.block;

import mc.replay.common.dispatcher.DispatcherPacket;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.nms.v1_16_5.recordable.block.RecBlockBreak;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockBreak;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;

public class BlockBreakPacketOutConverter implements DispatcherPacket<PacketPlayOutBlockBreak> {

    @Override
    public @Nullable List<Recordable> getRecordable(PacketPlayOutBlockBreak packet) {
        try {
            Field positionField = packet.getClass().getDeclaredField("c");
            positionField.setAccessible(true);

            Object position = positionField.get(packet);
            int x = (int) JavaReflections.getMethod(position.getClass(), "getX").invoke(position);
            int y = (int) JavaReflections.getMethod(position.getClass(), "getY").invoke(position);
            int z = (int) JavaReflections.getMethod(position.getClass(), "getZ").invoke(position);

            Vector blockPosition = new Vector(x, y, z);

            Field blockDataField = packet.getClass().getDeclaredField("d");
            blockDataField.setAccessible(true);

            Object blockDataObject = blockDataField.get(packet);
            BlockData craftBlockData = (BlockData) blockDataObject.getClass().getMethod("createCraftBlockData").invoke(blockDataObject);

            Field digTypeField = packet.getClass().getDeclaredField("a");
            digTypeField.setAccessible(true);

            Object digType = digTypeField.get(packet);
            String digTypeName = (String) digType.getClass().getMethod("name").invoke(digType);
            boolean instaBreak = JavaReflections.getField(packet.getClass(), "e", boolean.class).get(packet);

            return List.of(RecBlockBreak.of(blockPosition, craftBlockData, digTypeName, instaBreak));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}