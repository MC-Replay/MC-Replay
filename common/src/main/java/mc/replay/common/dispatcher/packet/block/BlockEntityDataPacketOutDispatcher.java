package mc.replay.common.dispatcher.packet.block;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.block.RecBlockEntityData;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockEntityDataPacket;

import java.util.List;

public final class BlockEntityDataPacketOutDispatcher implements DispatcherPacketOut<ClientboundBlockEntityDataPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundBlockEntityDataPacket packet) {
        return List.of(new RecBlockEntityData(
                packet.blockPosition(),
                packet.action(),
                packet.data()
        ));
    }
}