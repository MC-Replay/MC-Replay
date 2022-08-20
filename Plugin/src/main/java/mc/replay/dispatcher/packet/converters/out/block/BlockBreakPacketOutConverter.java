package mc.replay.dispatcher.packet.converters.out.block;

import mc.replay.dispatcher.packet.converters.ReplayPacketOutConverter;
import mc.replay.recordables.block.RecBlockBreak;
import mc.replay.common.utils.reflection.JavaReflections;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class BlockBreakPacketOutConverter implements ReplayPacketOutConverter<RecBlockBreak> {

    @Override
    public @NotNull String packetClassName() {
        return "PacketPlayOutBlockBreak";
    }

    @Override
    public @Nullable RecBlockBreak recordableFromPacket(Object packet) {
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

            return RecBlockBreak.of(blockPosition, craftBlockData, digTypeName, instaBreak);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}