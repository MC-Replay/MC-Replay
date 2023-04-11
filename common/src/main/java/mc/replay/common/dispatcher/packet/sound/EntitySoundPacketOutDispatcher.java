package mc.replay.common.dispatcher.packet.sound;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.sound.RecEntitySound;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntitySoundEffectPacket;

import java.util.List;

public final class EntitySoundPacketOutDispatcher implements DispatcherPacketOut<ClientboundEntitySoundEffectPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundEntitySoundEffectPacket packet) {
        return List.of(new RecEntitySound(
                packet.soundId(),
                packet.sourceId(),
                packet.entityId(),
                packet.volume(),
                packet.pitch()
        ));
    }
}