package mc.replay.dispatcher.packet.converters.out.particles;

import mc.replay.dispatcher.packet.converters.ReplayPacketOutConverter;
import mc.replay.nms.v1_16_5.recordable.particle.RecWorldParticles;
import mc.replay.common.utils.reflection.JavaReflections;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class WorldParticlesPacketOutConverter implements ReplayPacketOutConverter<RecWorldParticles> {

    @Override
    public @NotNull String packetClassName() {
        return "PacketPlayOutWorldParticles";
    }

    @Override
    public @Nullable RecWorldParticles recordableFromPacket(Object packet) {
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

            return RecWorldParticles.of(
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
            );
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}