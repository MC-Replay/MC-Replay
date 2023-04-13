package mc.replay.common.dispatcher.packet.sound;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.sound.RecCustomSoundEffect;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundCustomSoundEffectPacket;

import java.util.List;

public final class CustomSoundEffectPacketOutDispatcher implements DispatcherPacketOut<ClientboundCustomSoundEffectPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundCustomSoundEffectPacket packet) {
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