package mc.replay.common.recordables.actions.entity.miscellaneous;

import mc.replay.api.recording.recordables.action.EntityRecordableAction;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.api.recording.recordables.entity.RecordableEntityData;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntitySetPassengers;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundSetPassengersPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public record RecEntitySetPassengersAction() implements EntityRecordableAction<RecEntitySetPassengers> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntitySetPassengers recordable, @UnknownNullability Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(recordable.vehicleEntityid().entityId());
        if (data == null) return List.of();

        List<Integer> passengers = new ArrayList<>(); // Could be empty to dismount all passengers
        for (EntityId passengerId : recordable.passengerIds()) {
            RecordableEntityData passengerData = function.apply(passengerId.entityId());
            if (passengerData == null) continue;

            passengers.add(passengerData.entityId());
        }

        return List.of(
                new ClientboundSetPassengersPacket(
                        data.entityId(),
                        passengers
                )
        );
    }
}