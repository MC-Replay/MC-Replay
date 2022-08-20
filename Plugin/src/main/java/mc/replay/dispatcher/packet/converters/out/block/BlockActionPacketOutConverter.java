package mc.replay.dispatcher.packet.converters.out.block;

import mc.replay.dispatcher.packet.converters.ReplayPacketOutConverter;
import mc.replay.recordables.block.RecBlockAction;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.common.utils.reflection.MinecraftReflections;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class BlockActionPacketOutConverter implements ReplayPacketOutConverter<RecBlockAction> {

    @Override
    public @NotNull String packetClassName() {
        return "PacketPlayOutBlockAction";
    }

    @Override
    public @Nullable RecBlockAction recordableFromPacket(Object packet) {
        try {
            Field positionField = packet.getClass().getDeclaredField("a");
            positionField.setAccessible(true);

            Object position = positionField.get(packet);
            int x = (int) JavaReflections.getMethod(position.getClass(), "getX").invoke(position);
            int y = (int) JavaReflections.getMethod(position.getClass(), "getY").invoke(position);
            int z = (int) JavaReflections.getMethod(position.getClass(), "getZ").invoke(position);

            Vector blockPosition = new Vector(x, y, z);

            int actionId = JavaReflections.getField(packet.getClass(), "b", int.class).get(packet);
            int actionParam = JavaReflections.getField(packet.getClass(), "c", int.class).get(packet);

            Field blockField = packet.getClass().getDeclaredField("d");
            blockField.setAccessible(true);
            Object blockObject = blockField.get(packet);

            Class<?> registryClass = MinecraftReflections.nmsClass("", "IRegistry");
            Object blockRegistry = registryClass.getField("BLOCK").get(null);
            int blockId = (int) blockRegistry.getClass().getMethod("a", Object.class).invoke(blockRegistry, blockObject);

            return RecBlockAction.of(blockPosition, blockId, actionId, actionParam);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}