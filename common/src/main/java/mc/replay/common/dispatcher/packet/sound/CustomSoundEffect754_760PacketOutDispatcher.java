package mc.replay.common.dispatcher.packet.sound;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.sound.RecCustomSoundEffect;
import mc.replay.packetlib.network.packet.clientbound.play.legacy.ClientboundCustomSoundEffect_754_760Packet;

import java.util.List;

public final class CustomSoundEffect754_760PacketOutDispatcher implements DispatcherPacketOut<ClientboundCustomSoundEffect_754_760Packet> {

    @Override
    public List<Recordable> getRecordables(ClientboundCustomSoundEffect_754_760Packet packet) {
        return List.of(
                new RecCustomSoundEffect(
                        packet.soundName(),
                        packet.sourceId(),
                        packet.x(),
                        packet.y(),
                        packet.z(),
                        packet.volume(),
                        packet.pitch(),
                        packet.seed()
                )
        );
    }
}