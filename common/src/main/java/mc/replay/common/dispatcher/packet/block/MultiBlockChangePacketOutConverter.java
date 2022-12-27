package mc.replay.common.dispatcher.packet.block;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.block.RecMultiBlockChange;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundMultiBlockChangePacket;

import java.util.List;
import java.util.function.Function;

public final class MultiBlockChangePacketOutConverter implements DispatcherPacketOut<ClientboundMultiBlockChangePacket> {

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(ClientboundMultiBlockChangePacket packet) {
        return List.of(RecMultiBlockChange.of(
                packet.chunkSectionPosition(),
                packet.suppressLightUpdates(),
                packet.blocks()
        ));
    }
}