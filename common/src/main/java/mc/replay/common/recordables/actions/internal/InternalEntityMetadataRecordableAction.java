package mc.replay.common.recordables.actions.internal;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.api.recordables.action.RecordableAction;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.common.recordables.types.internal.EntityStateRecordable;
import mc.replay.common.replay.IReplayEntityProvider;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface InternalEntityMetadataRecordableAction<R extends EntityStateRecordable> extends RecordableAction<R, IReplayEntityProvider> {

    @Override
    default @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull R recordable, @UnknownNullability IReplayEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        EntityMetadata entityMetadata = data.entity().getMetadata();
        Metadata metadata = entityMetadata.getMetadata();
        metadata.detectChanges(true);

        this.writeMetadata(recordable, entityMetadata);

        Map<Integer, Metadata.Entry<?>> changes = metadata.getChanges();
        metadata.detectChanges(false);

        if (changes == null || changes.isEmpty()) return List.of();

        return List.of(
                new ClientboundEntityMetadataPacket(
                        data.entityId(),
                        changes
                )
        );
    }

    void writeMetadata(@NotNull R recordable, @NotNull EntityMetadata entityMetadata);
}