package mc.replay.common.recordables.types.entity;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecEntitySpawn(EntityId entityId, EntityType entityType, Pos position,
                             int data, Vector velocity) implements Recordable {

    public RecEntitySpawn(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.readEnum(EntityType.class),
                new Pos(
                        reader.read(DOUBLE),
                        reader.read(DOUBLE),
                        reader.read(DOUBLE),
                        reader.read(FLOAT),
                        reader.read(FLOAT)
                ),
                reader.read(VAR_INT),
                (reader.read(BOOLEAN)) ? new Vector(
                        reader.read(DOUBLE),
                        reader.read(DOUBLE),
                        reader.read(DOUBLE)
                ) : null
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.writeEnum(EntityType.class, this.entityType);
        writer.write(DOUBLE, this.position.x());
        writer.write(DOUBLE, this.position.y());
        writer.write(DOUBLE, this.position.z());
        writer.write(FLOAT, this.position.yaw());
        writer.write(FLOAT, this.position.pitch());

        writer.write(VAR_INT, this.data);
        writer.write(BOOLEAN, this.velocity != null);
        if (this.velocity != null) {
            writer.write(DOUBLE, this.velocity.getX());
            writer.write(DOUBLE, this.velocity.getY());
            writer.write(DOUBLE, this.velocity.getZ());
        }
    }
}