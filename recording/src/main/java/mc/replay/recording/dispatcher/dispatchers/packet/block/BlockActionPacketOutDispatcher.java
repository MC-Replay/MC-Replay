package mc.replay.recording.dispatcher.dispatchers.packet.block;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.recordables.types.block.RecBlockAction;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockActionPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;

import java.util.List;

public final class BlockActionPacketOutDispatcher implements DispatcherPacketOut<ClientboundBlockActionPacket> {

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundBlockActionPacket packet) {
        return List.of(
                new RecBlockAction(
                        packet.blockPosition(),
                        packet.blockId(),
                        packet.actionId(),
                        packet.actionParam()
                )
        );
    }
}