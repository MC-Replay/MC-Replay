package mc.replay.common.dispatcher.packet.entity.miscellaneous;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntitySetPassengers;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundSetPassengersPacket;

import java.util.List;

public final class EntitySetPassengersPacketOutDispatcher implements DispatcherPacketOut<ClientboundSetPassengersPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundSetPassengersPacket packet) {
        return List.of(
                new RecEntitySetPassengers(
                        EntityId.of(packet.vehicleEntityId()),
                        packet.passengerIds().stream()
                                .map(EntityId::of)
                                .toList()
                )
        );
    }
}