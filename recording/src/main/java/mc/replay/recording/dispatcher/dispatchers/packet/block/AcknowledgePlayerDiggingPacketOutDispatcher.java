package mc.replay.recording.dispatcher.dispatchers.packet.block;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.recordables.types.block.RecAcknowledgePlayerDigging;
import mc.replay.packetlib.network.packet.clientbound.play.legacy.ClientboundAcknowledgePlayerDigging754_758Packet;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;

import java.util.List;

public final class AcknowledgePlayerDiggingPacketOutDispatcher implements DispatcherPacketOut<ClientboundAcknowledgePlayerDigging754_758Packet> {

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundAcknowledgePlayerDigging754_758Packet packet) {
        return List.of(
                new RecAcknowledgePlayerDigging(
                        packet.blockPosition(),
                        packet.blockStateId(),
                        packet.stateId(),
                        packet.successful()
                )
        );
    }
}