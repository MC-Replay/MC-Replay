package mc.replay.common.dispatcher.packet.sound;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.sound.RecSoundEffect;
import mc.replay.packetlib.network.packet.clientbound.ClientboundSoundEffectPacket;

import java.util.List;
import java.util.function.Function;

public final class SoundEffectPacketOutConverter implements DispatcherPacketOut<ClientboundSoundEffectPacket> {

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(ClientboundSoundEffectPacket packet) {
        return List.of(RecSoundEffect.of(
                packet.soundId(),
                packet.sourceId(),
                packet.x(),
                packet.y(),
                packet.z(),
                packet.volume(),
                packet.pitch(),
                packet.seed()
        ));
    }
}