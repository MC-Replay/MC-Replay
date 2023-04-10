package mc.replay.common.recordables.entity;

import mc.replay.api.recording.recordables.DependentRecordableData;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.interfaces.RecordableEntity;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static mc.replay.packetlib.network.ReplayByteBuffer.BYTE;
import static mc.replay.packetlib.network.ReplayByteBuffer.VAR_INT;

public record RecEntitySpawnMetadata(EntityId entityId,
                                     Map<Integer, Metadata.Entry<?>> metadata) implements RecordableEntity {

    public RecEntitySpawnMetadata(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                readMetadata(reader)
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());
        if (data == null) return new ArrayList<>();

        data.entity().addMetadata(this.metadata);

        return List.of(
                new ClientboundEntityMetadataPacket(
                        data.entityId(),
                        this.metadata
                )
        );
    }

    @Override
    public DependentRecordableData<?> depend() {
        return new DependentRecordableData<>(RecEntitySpawn.class, (rec) -> rec.entityId().entityId() == this.entityId.entityId());
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);

        for (Map.Entry<Integer, Metadata.Entry<?>> entry : this.metadata.entrySet()) {
            writer.write(BYTE, entry.getKey().byteValue());
            writer.write(entry.getValue());
        }
        writer.write(BYTE, (byte) 0xFF); // End
    }

    private static Map<Integer, Metadata.Entry<?>> readMetadata(@NotNull ReplayByteBuffer reader) {
        Map<Integer, Metadata.Entry<?>> entries = new HashMap<>();
        byte index;
        while ((index = reader.read(BYTE)) != (byte) 0xFF) {
            final int type = reader.read(VAR_INT);
            entries.put((int) index, Metadata.Entry.read(type, reader));
        }
        return entries;
    }
}