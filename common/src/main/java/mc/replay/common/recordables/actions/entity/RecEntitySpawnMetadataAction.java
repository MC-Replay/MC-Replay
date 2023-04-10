package mc.replay.common.recordables.actions.entity;

import mc.replay.api.recording.recordables.action.EntityRecordableAction;
import mc.replay.api.recording.recordables.entity.RecordableEntityData;
import mc.replay.common.recordables.types.entity.RecEntitySpawnMetadata;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntitySpawnMetadataAction() implements EntityRecordableAction<RecEntitySpawnMetadata> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntitySpawnMetadata recordable, @NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(recordable.entityId().entityId());
        if (data == null) return List.of();

        data.entity().addMetadata(recordable.metadata());

        return List.of(
                new ClientboundEntityMetadataPacket(
                        data.entityId(),
                        recordable.metadata()
                )
        );
    }
}