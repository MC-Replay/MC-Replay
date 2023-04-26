package mc.replay.common.recordables.types.entity.movement;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.RecordableBufferTypes;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public record RecEntityPosition(EntityId entityId, Pos position, float pitch) implements Recordable {

    public RecEntityPosition(EntityId entityId, @NotNull Location location) {
        this(
                entityId,
                Pos.of(
                        location.getX(),
                        location.getY(),
                        location.getZ()
                ),
                location.getPitch()
        );
    }

    public RecEntityPosition(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(RecordableBufferTypes.ENTITY_POSITION),
                reader.read(RecordableBufferTypes.ENTITY_ROTATION)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(RecordableBufferTypes.ENTITY_POSITION, this.position);
        writer.write(RecordableBufferTypes.ENTITY_ROTATION, this.pitch);
    }
}