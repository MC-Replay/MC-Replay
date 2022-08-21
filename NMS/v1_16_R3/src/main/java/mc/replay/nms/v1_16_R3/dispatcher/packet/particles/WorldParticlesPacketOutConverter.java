package mc.replay.nms.v1_16_R3.dispatcher.packet.particles;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.PacketConverter;
import mc.replay.nms.v1_16_R3.recordable.particle.RecWorldParticles;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_16_R3.ParticleParam;

import java.util.List;

public final class WorldParticlesPacketOutConverter implements DispatcherPacketOut<PacketPlayOutWorldParticles> {

    @Override
    public List<Recordable> getRecordables(PacketPlayOutWorldParticles packet) {
        PacketConverter.ConvertedPacket convertedPacket = this.convert(packet);

        ParticleParam particleParam = convertedPacket.get("j", ParticleParam.class);

        double x = convertedPacket.get("a", Double.class);
        double y = convertedPacket.get("b", Double.class);
        double z = convertedPacket.get("c", Double.class);

        float offsetX = convertedPacket.get("d", Float.class);
        float offsetY = convertedPacket.get("e", Float.class);
        float offsetZ = convertedPacket.get("f", Float.class);

        float particleData = convertedPacket.get("g", Float.class);
        int particleCount = convertedPacket.get("h", Integer.class);

        boolean longDistance = convertedPacket.get("i", Boolean.class);

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
    }
}