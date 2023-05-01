package mc.replay.recording.dispatcher.dispatchers.packet.block;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.recordables.types.block.RecBlockEntityData;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockEntityDataPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;

import java.util.List;

public final class BlockEntityDataPacketOutDispatcher implements DispatcherPacketOut<ClientboundBlockEntityDataPacket> {

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundBlockEntityDataPacket packet) {
        return List.of(
                new RecBlockEntityData(
                        packet.blockPosition(),
                        packet.action(),
                        packet.data()
                )
        );
    }
}