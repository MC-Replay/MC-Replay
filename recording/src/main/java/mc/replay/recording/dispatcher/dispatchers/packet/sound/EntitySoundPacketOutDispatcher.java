package mc.replay.recording.dispatcher.dispatchers.packet.sound;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.recordables.types.sound.RecEntitySound;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntitySoundEffectPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;

import java.util.List;

public final class EntitySoundPacketOutDispatcher extends DispatcherPacketOut<ClientboundEntitySoundEffectPacket> {

    private EntitySoundPacketOutDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundEntitySoundEffectPacket packet) {
        return List.of(
                new RecEntitySound(
                        packet.soundId(),
                        packet.soundName(),
                        packet.range(),
                        packet.sourceId(),
                        packet.entityId(),
                        packet.volume(),
                        packet.pitch(),
                        packet.seed()
                )
        );
    }
}