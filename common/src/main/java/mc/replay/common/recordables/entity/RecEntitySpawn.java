package mc.replay.common.recordables.entity;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.interfaces.RecordableOther;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.wrapper.entity.EntityWrapper;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecEntitySpawn(EntityId entityId, EntityType entityType, Pos position,
                             Map<Integer, Metadata.Entry<?>> metadata,
                             Vector velocity) implements RecordableOther {

    public RecEntitySpawn(EntityId entityId, EntityWrapper entityWrapper) {
        this(
                entityId,
                entityWrapper.getType(),
                entityWrapper.getPosition(),
                entityWrapper.getMetadata().getEntries(),
                entityWrapper.getVelocity()
        );
    }

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
                readMetadata(reader),
                (reader.read(BOOLEAN)) ? new Vector(
                        reader.read(FLOAT),
                        reader.read(FLOAT),
                        reader.read(FLOAT)
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

        for (Map.Entry<Integer, Metadata.Entry<?>> entry : this.metadata.entrySet()) {
            writer.write(BYTE, entry.getKey().byteValue());
            writer.write(entry.getValue());
        }
        writer.write(BYTE, (byte) 0xFF); // End

        writer.write(BOOLEAN, this.velocity != null);
        if (this.velocity != null) {
            writer.write(DOUBLE, this.velocity.getX());
            writer.write(DOUBLE, this.velocity.getY());
            writer.write(DOUBLE, this.velocity.getZ());
        }
    }

    private static Map<Integer, Metadata.Entry<?>> readMetadata(@NotNull ReplayByteBuffer reader) {
        Map<Integer, Metadata.Entry<?>> entries = new HashMap<>();
        while (true) {
            final byte index = reader.read(BYTE);
            if (index == (byte) 0xFF) break; // End
            final int type = reader.read(VAR_INT);
            entries.put((int) index, Metadata.Entry.read(type, reader));
        }
        return entries;
    }
}