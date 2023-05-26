package mc.replay.common.recordables.actions.entity.metadata.flying;

import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.common.recordables.types.entity.metadata.flying.RecPhantomSize;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.flying.PhantomMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.Map;

public record RecPhantomSizeAction() implements EntityRecordableAction<RecPhantomSize> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecPhantomSize recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        EntityMetadata entityMetadata = data.entity().getMetadata();
        if (entityMetadata instanceof PhantomMetadata phantomMetadata) {
            Metadata metadata = phantomMetadata.getMetadata();

            metadata.detectChanges(true);

            phantomMetadata.setSize(recordable.size());

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

        return List.of();
    }
}