package mc.replay.recording.dispatcher.dispatchers.packet.block;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.recordables.types.block.RecBlockBreakStage;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockBreakAnimationPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;

import java.util.List;

public final class BlockBreakAnimationPacketOutDispatcher extends DispatcherPacketOut<ClientboundBlockBreakAnimationPacket> {

    private BlockBreakAnimationPacketOutDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundBlockBreakAnimationPacket packet) {
        return List.of(
                new RecBlockBreakStage(
                        packet.entityId(),
                        packet.blockPosition(),
                        packet.stage()
                )
        );
    }
}