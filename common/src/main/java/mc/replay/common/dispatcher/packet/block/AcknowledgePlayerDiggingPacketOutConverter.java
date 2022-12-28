package mc.replay.common.dispatcher.packet.block;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.block.RecAcknowledgePlayerDigging;
import mc.replay.packetlib.network.packet.clientbound.play.version.ClientboundAcknowledgePlayerDigging754_758Packet;

import java.util.List;
import java.util.function.Function;

public final class AcknowledgePlayerDiggingPacketOutConverter implements DispatcherPacketOut<ClientboundAcknowledgePlayerDigging754_758Packet> {

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(ClientboundAcknowledgePlayerDigging754_758Packet packet) {
        return List.of(new RecAcknowledgePlayerDigging(
                packet.blockPosition(),
                packet.blockStateId(),
                packet.stateId(),
                packet.successful()
        ));
    }
}