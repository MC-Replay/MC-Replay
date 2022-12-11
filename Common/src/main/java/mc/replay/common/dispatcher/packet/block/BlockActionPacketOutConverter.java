package mc.replay.common.dispatcher.packet.block;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.block.RecBlockAction;
import mc.replay.packetlib.network.packet.clientbound.ClientboundBlockActionPacket;

import java.util.List;
import java.util.function.Function;

public final class BlockActionPacketOutConverter implements DispatcherPacketOut<ClientboundBlockActionPacket> {

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(ClientboundBlockActionPacket packet) {
        return List.of(RecBlockAction.of(
                packet.blockPosition(),
                packet.blockId(),
                packet.actionId(),
                packet.actionParam()
        ));
    }
}