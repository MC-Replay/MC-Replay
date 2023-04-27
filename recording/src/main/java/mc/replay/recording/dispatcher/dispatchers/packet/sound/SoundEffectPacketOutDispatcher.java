package mc.replay.recording.dispatcher.dispatchers.packet.sound;

import mc.replay.api.recordables.Recordable;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.common.recordables.types.sound.RecSoundEffect;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundSoundEffectPacket;

import java.util.List;

public final class SoundEffectPacketOutDispatcher implements DispatcherPacketOut<ClientboundSoundEffectPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundSoundEffectPacket packet) {
        return List.of(
                new RecSoundEffect(
                        packet.soundId(),
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