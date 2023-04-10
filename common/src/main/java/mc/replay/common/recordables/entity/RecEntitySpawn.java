package mc.replay.common.recordables.entity;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.interfaces.RecordableOther;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecEntitySpawn(EntityId entityId, EntityType entityType, Pos position,
                             Vector velocity) implements RecordableOther {

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
                (reader.read(BOOLEAN)) ? new Vector(
                        reader.read(DOUBLE),
                        reader.read(DOUBLE),
                        reader.read(DOUBLE)
                ) : null
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of();
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

        writer.write(BOOLEAN, this.velocity != null);
        if (this.velocity != null) {
            writer.write(DOUBLE, this.velocity.getX());
            writer.write(DOUBLE, this.velocity.getY());
            writer.write(DOUBLE, this.velocity.getZ());
        }
    }
}