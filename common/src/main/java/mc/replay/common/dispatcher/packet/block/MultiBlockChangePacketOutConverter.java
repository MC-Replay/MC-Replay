package mc.replay.common.dispatcher.packet.block;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.block.RecMultiBlockChange;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundMultiBlockChangePacket;

import java.util.List;

public final class MultiBlockChangePacketOutConverter implements DispatcherPacketOut<ClientboundMultiBlockChangePacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundMultiBlockChangePacket packet) {
        return List.of(new RecMultiBlockChange(
                packet.chunkSectionPosition(),
                packet.suppressLightUpdates(),
                packet.blocks()
        ));
    }
}