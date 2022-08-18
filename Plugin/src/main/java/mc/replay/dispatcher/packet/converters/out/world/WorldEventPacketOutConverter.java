package mc.replay.dispatcher.packet.converters.out.world;

import mc.replay.dispatcher.packet.converters.ReplayPacketOutConverter;
import mc.replay.recordables.world.RecWorldEvent;
import mc.replay.utils.reflection.JavaReflections;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class WorldEventPacketOutConverter implements ReplayPacketOutConverter<RecWorldEvent> {

    @Override
    public @NotNull String packetClassName() {
        return "PacketPlayOutWorldEvent";
    }

    @Override
    public @Nullable RecWorldEvent recordableFromPacket(Object packet) {
        try {
            int effectId = JavaReflections.getField(packet.getClass(), "a", int.class).get(packet);

            Field positionField = packet.getClass().getDeclaredField("b");
            positionField.setAccessible(true);

            Object positionObject = positionField.get(packet);
            int x = (int) JavaReflections.getMethod(positionObject.getClass(), "getX").invoke(positionObject);
            int y = (int) JavaReflections.getMethod(positionObject.getClass(), "getY").invoke(positionObject);
            int z = (int) JavaReflections.getMethod(positionObject.getClass(), "getZ").invoke(positionObject);

            Vector position = new Vector(x, y, z);

            int data = JavaReflections.getField(packet.getClass(), "c", int.class).get(packet);
            boolean disableRelativeVolume = JavaReflections.getField(packet.getClass(), "d", boolean.class).get(packet);

            return RecWorldEvent.of(effectId, position, data, disableRelativeVolume);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}