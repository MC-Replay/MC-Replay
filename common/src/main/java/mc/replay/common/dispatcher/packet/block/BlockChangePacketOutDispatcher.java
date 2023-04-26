package mc.replay.common.dispatcher.packet.block;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.block.RecBlockChange;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockChangePacket;

import java.util.List;

public final class BlockChangePacketOutDispatcher implements DispatcherPacketOut<ClientboundBlockChangePacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundBlockChangePacket packet) {
        return List.of(
                new RecBlockChange(
                        packet.blockPosition(),
                        packet.blockStateId()
                )
        );
    }
}