package mc.replay.nms.v1_16_R3.dispatcher.packet.world;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.nms.v1_16_R3.recordable.world.RecWorldEvent;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldEvent;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.List;

public final class WorldEventPacketOutConverter implements DispatcherPacketOut<PacketPlayOutWorldEvent> {

    @Override
    public List<Recordable> getRecordables(PacketPlayOutWorldEvent packet) {
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

            return List.of(RecWorldEvent.of(effectId, position, data, disableRelativeVolume));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}