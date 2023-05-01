package mc.replay.common.recordables.actions.entity.metadata;

import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.common.recordables.types.entity.metadata.RecEntitySwimming;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import org.bukkit.entity.Pose;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.Map;

public record RecEntitySwimmingAction() implements EntityRecordableAction<RecEntitySwimming> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntitySwimming recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        EntityMetadata entityMetadata = data.entity().getMetadata();
        Metadata metadata = entityMetadata.getMetadata();

        metadata.detectChanges(true);

        entityMetadata.setSwimming(recordable.swimming());
        entityMetadata.setPose(recordable.swimming() ? Pose.SWIMMING : Pose.STANDING);

        Map<Integer, Metadata.Entry<?>> changes = metadata.getChanges();
        metadata.detectChanges(false);

        if (changes == null || changes.isEmpty()) return List.of();

        return List.of(
                new ClientboundEntityMetadataPacket(
                        data.entityId(),
                        entityMetadata.getEntries()
                )
        );
    }
}