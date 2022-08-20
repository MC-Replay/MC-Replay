package mc.replay.nms.v1_16_5.dispatcher.packet.particles;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.nms.v1_16_5.recordable.particle.RecWorldParticles;
import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldParticles;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;

public class WorldParticlesPacketOutConverter implements DispatcherPacketOut<PacketPlayOutWorldParticles> {

    @Override
    public @Nullable List<Recordable> getRecordable(Object packetClass) {
        PacketPlayOutWorldParticles packet = (PacketPlayOutWorldParticles) packetClass;

        try {
            Field particleParamField = packet.getClass().getDeclaredField("j");
            particleParamField.setAccessible(true);
            Object particleParam = particleParamField.get(packet);

            double x = JavaReflections.getField(packet.getClass(), "a", double.class).get(packet);
            double y = JavaReflections.getField(packet.getClass(), "b", double.class).get(packet);
            double z = JavaReflections.getField(packet.getClass(), "c", double.class).get(packet);

            float offsetX = JavaReflections.getField(packet.getClass(), "d", float.class).get(packet);
            float offsetY = JavaReflections.getField(packet.getClass(), "e", float.class).get(packet);
            float offsetZ = JavaReflections.getField(packet.getClass(), "f", float.class).get(packet);

            float particleData = JavaReflections.getField(packet.getClass(), "g", float.class).get(packet);
            int particleCount = JavaReflections.getField(packet.getClass(), "h", int.class).get(packet);

            boolean longDistance = JavaReflections.getField(packet.getClass(), "i", boolean.class).get(packet);

            return List.of(RecWorldParticles.of(
                    particleParam,
                    longDistance,
                    x,
                    y,
                    z,
                    offsetX,
                    offsetY,
                    offsetZ,
                    particleData,
                    particleCount
            ));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}