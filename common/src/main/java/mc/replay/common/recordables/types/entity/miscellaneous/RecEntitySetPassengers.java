package mc.replay.common.recordables.types.entity.miscellaneous;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record RecEntitySetPassengers(EntityId vehicleEntityid, List<EntityId> passengerIds) implements Recordable {

    public RecEntitySetPassengers(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.readCollection(EntityId::new)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.vehicleEntityid);
        writer.writeCollection(this.passengerIds);
    }
}