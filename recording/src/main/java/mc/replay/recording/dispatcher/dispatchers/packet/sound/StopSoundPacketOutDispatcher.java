package mc.replay.recording.dispatcher.dispatchers.packet.sound;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.recordables.types.sound.RecStopSound;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundStopSoundPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;

import java.util.List;

public final class StopSoundPacketOutDispatcher extends DispatcherPacketOut<ClientboundStopSoundPacket> {

    private StopSoundPacketOutDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundStopSoundPacket packet) {
        return List.of(
                new RecStopSound(
                        packet.flags(),
                        packet.sourceId(),
                        packet.sound()
                )
        );
    }
}