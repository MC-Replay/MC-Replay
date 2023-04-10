package mc.replay.common.dispatcher.packet.block;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.block.RecBlockAction;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockActionPacket;

import java.util.List;

public final class BlockActionPacketOutConverter implements DispatcherPacketOut<ClientboundBlockActionPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundBlockActionPacket packet) {
        return List.of(new RecBlockAction(
                packet.blockPosition(),
                packet.blockId(),
                packet.actionId(),
                packet.actionParam()
        ));
    }
}