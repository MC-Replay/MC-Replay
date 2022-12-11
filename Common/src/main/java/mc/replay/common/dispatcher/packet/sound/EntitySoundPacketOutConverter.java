package mc.replay.common.dispatcher.packet.sound;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.sound.RecEntitySound;
import mc.replay.packetlib.network.packet.clientbound.ClientboundEntitySoundEffectPacket;

import java.util.List;
import java.util.function.Function;

public final class EntitySoundPacketOutConverter implements DispatcherPacketOut<ClientboundEntitySoundEffectPacket> {

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(ClientboundEntitySoundEffectPacket packet) {
        return List.of(RecEntitySound.of(
                packet.soundId(),
                packet.sourceId(),
                packet.entityId(),
                packet.volume(),
                packet.pitch()
        ));
    }
}