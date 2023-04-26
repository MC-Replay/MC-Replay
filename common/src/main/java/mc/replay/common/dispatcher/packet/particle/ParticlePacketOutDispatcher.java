package mc.replay.common.dispatcher.packet.particle;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.particle.RecParticle;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundParticlePacket;

import java.util.List;

public final class ParticlePacketOutDispatcher implements DispatcherPacketOut<ClientboundParticlePacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundParticlePacket packet) {
        return List.of(
                new RecParticle(
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
                )
        );
    }
}