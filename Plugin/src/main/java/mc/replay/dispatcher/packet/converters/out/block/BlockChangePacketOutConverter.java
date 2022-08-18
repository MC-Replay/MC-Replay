package mc.replay.dispatcher.packet.converters.out.block;

import mc.replay.dispatcher.packet.converters.ReplayPacketOutConverter;
import mc.replay.recordables.block.RecBlockChange;
import mc.replay.utils.reflection.JavaReflections;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class BlockChangePacketOutConverter implements ReplayPacketOutConverter<RecBlockChange> {

    @Override
    public @NotNull String packetClassName() {
        return "PacketPlayOutBlockChange";
    }

    @Override
    public @Nullable RecBlockChange recordableFromPacket(Object packet) {
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

            return RecBlockChange.of(blockPosition, craftBlockData);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}