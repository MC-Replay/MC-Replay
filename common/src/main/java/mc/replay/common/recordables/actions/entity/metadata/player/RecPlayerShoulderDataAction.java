package mc.replay.common.recordables.actions.entity.metadata.player;

import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.common.recordables.types.entity.metadata.player.RecPlayerShoulderData;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.PlayerMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.Map;

public record RecPlayerShoulderDataAction() implements EntityRecordableAction<RecPlayerShoulderData> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecPlayerShoulderData recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        EntityMetadata entityMetadata = data.entity().getMetadata();
        if (entityMetadata instanceof PlayerMetadata playerMetadata) {
            Metadata metadata = playerMetadata.getMetadata();

            metadata.detectChanges(true);

            if (recordable.right()) {
                playerMetadata.setRightShouldEntityData(recordable.shoulderData());
            } else {
                playerMetadata.setLeftShouldEntityData(recordable.shoulderData());
            }

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