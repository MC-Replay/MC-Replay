package mc.replay.common.dispatcher.packet.block;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.block.RecBlockEntityData;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockEntityDataPacket;

import java.util.List;
import java.util.function.Function;

public final class BlockEntityDataPacketOutConverter implements DispatcherPacketOut<ClientboundBlockEntityDataPacket> {

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(ClientboundBlockEntityDataPacket packet) {
        return List.of(new RecBlockEntityData(
                packet.blockPosition(),
                packet.action(),
                packet.data()
        ));
    }
}