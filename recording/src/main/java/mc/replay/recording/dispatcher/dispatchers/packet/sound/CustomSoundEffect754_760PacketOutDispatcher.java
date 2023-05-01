package mc.replay.recording.dispatcher.dispatchers.packet.sound;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.recordables.types.sound.RecCustomSoundEffect;
import mc.replay.packetlib.network.packet.clientbound.play.legacy.ClientboundCustomSoundEffect_754_760Packet;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;

import java.util.List;

public final class CustomSoundEffect754_760PacketOutDispatcher extends DispatcherPacketOut<ClientboundCustomSoundEffect_754_760Packet> {

    private CustomSoundEffect754_760PacketOutDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundCustomSoundEffect_754_760Packet packet) {
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