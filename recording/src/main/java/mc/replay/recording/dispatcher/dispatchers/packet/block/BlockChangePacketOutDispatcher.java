package mc.replay.recording.dispatcher.dispatchers.packet.block;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.recordables.types.block.RecBlockChange;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockChangePacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;

import java.util.List;

public final class BlockChangePacketOutDispatcher extends DispatcherPacketOut<ClientboundBlockChangePacket> {

    private BlockChangePacketOutDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundBlockChangePacket packet) {
        return List.of(
                new RecBlockChange(
                        packet.blockPosition(),
                        packet.blockStateId()
                )
        );
    }
}