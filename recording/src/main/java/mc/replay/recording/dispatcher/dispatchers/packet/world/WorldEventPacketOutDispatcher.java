package mc.replay.recording.dispatcher.dispatchers.packet.world;

import mc.replay.api.recordables.Recordable;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.common.recordables.types.world.RecWorldEvent;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundWorldEventPacket;

import java.util.List;

public final class WorldEventPacketOutDispatcher implements DispatcherPacketOut<ClientboundWorldEventPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundWorldEventPacket packet) {
        return List.of(
                new RecWorldEvent(
                        packet.effectId(),
                        packet.position(),
                        packet.data(),
                        packet.disableRelativeVolume()
                )
        );
    }
}