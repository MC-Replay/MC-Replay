package mc.replay.common.recordables.types.entity.movement;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableBufferTypes;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.BOOLEAN;

public record RecEntityTeleport(EntityId entityId, Pos position, boolean onGround) implements Recordable {

    public RecEntityTeleport(EntityId entityId, Location to, boolean onGround) {
        this(
                entityId,
                Pos.from(to),
                onGround
        );
    }

    public RecEntityTeleport(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(RecordableBufferTypes.ENTITY_POSITION_WITH_ROTATION),
                reader.read(BOOLEAN)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(RecordableBufferTypes.ENTITY_POSITION_WITH_ROTATION, this.position);
        writer.write(BOOLEAN, this.onGround);
    }
}