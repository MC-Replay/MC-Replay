package mc.replay.common.dispatcher.packet.block;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.block.RecBlockBreakStage;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockBreakAnimationPacket;

import java.util.List;

public final class BlockBreakAnimationPacketOutDispatcher implements DispatcherPacketOut<ClientboundBlockBreakAnimationPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundBlockBreakAnimationPacket packet) {
        return List.of(
                new RecBlockBreakStage(
                        packet.blockPosition(),
                        packet.stage()
                )
        );
    }
}