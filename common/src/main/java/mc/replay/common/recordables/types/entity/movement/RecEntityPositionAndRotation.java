package mc.replay.common.recordables.types.entity.movement;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableBufferTypes;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.BOOLEAN;

public record RecEntityPositionAndRotation(EntityId entityId, Pos deltaPosition,
                                           boolean onGround) implements Recordable {

    public RecEntityPositionAndRotation(EntityId entityId, @NotNull Location to, @NotNull Location from, boolean onGround) {
        this(
                entityId,
                Pos.from(to).subtract(Pos.from(from)),
                onGround
        );
    }

    public RecEntityPositionAndRotation(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(RecordableBufferTypes.ENTITY_POSITION_WITH_ROTATION),
                reader.read(BOOLEAN)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(RecordableBufferTypes.ENTITY_POSITION_WITH_ROTATION, this.deltaPosition);
        writer.write(BOOLEAN, this.onGround);
    }
}