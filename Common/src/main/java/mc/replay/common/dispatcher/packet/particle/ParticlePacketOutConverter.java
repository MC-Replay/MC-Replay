package mc.replay.common.dispatcher.packet.particle;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.particle.RecParticle;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundParticlePacket;

import java.util.List;
import java.util.function.Function;

public final class ParticlePacketOutConverter implements DispatcherPacketOut<ClientboundParticlePacket> {

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(ClientboundParticlePacket packet) {
        return List.of(RecParticle.of(
                packet.particleId(),
                packet.longDistance(),
                packet.x(),
                packet.y(),
                packet.z(),
                packet.offsetX(),
                packet.offsetY(),
                packet.offsetZ(),
                packet.particleData(),
                packet.particleCount(),
                packet.data()
        ));
    }
}