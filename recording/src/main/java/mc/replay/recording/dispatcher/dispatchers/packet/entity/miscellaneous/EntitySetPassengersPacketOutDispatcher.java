package mc.replay.recording.dispatcher.dispatchers.packet.entity.miscellaneous;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntitySetPassengers;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundSetPassengersPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;

import java.util.List;

public final class EntitySetPassengersPacketOutDispatcher extends DispatcherPacketOut<ClientboundSetPassengersPacket> {

    private EntitySetPassengersPacketOutDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundSetPassengersPacket packet) {
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