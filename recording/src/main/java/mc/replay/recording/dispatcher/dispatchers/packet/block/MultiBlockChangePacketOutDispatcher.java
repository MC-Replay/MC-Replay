package mc.replay.recording.dispatcher.dispatchers.packet.block;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.recordables.types.block.RecMultiBlockChange;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundMultiBlockChangePacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;

import java.util.List;

public final class MultiBlockChangePacketOutDispatcher extends DispatcherPacketOut<ClientboundMultiBlockChangePacket> {

    private MultiBlockChangePacketOutDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundMultiBlockChangePacket packet) {
        return List.of(
                new RecMultiBlockChange(
                        packet.chunkSectionPosition(),
                        packet.suppressLightUpdates(),
                        packet.blocks()
                )
        );
    }
}