package mc.replay.common.dispatcher.packet.block;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.block.RecBlockChange;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockChangePacket;

import java.util.List;
import java.util.function.Function;

public final class BlockChangePacketOutConverter implements DispatcherPacketOut<ClientboundBlockChangePacket> {

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(ClientboundBlockChangePacket packet) {
        return List.of(new RecBlockChange(
                packet.blockPosition(),
                packet.blockStateId()
        ));
    }
}