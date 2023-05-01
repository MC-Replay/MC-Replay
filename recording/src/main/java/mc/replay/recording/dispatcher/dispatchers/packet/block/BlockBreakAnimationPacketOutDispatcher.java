package mc.replay.recording.dispatcher.dispatchers.packet.block;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.recordables.types.block.RecBlockBreakStage;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockBreakAnimationPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;

import java.util.List;

public final class BlockBreakAnimationPacketOutDispatcher implements DispatcherPacketOut<ClientboundBlockBreakAnimationPacket> {

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundBlockBreakAnimationPacket packet) {
        return List.of(
                new RecBlockBreakStage(
                        packet.blockPosition(),
                        packet.stage()
                )
        );
    }
}