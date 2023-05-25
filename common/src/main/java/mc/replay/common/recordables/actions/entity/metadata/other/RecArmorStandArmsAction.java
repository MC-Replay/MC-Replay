package mc.replay.common.recordables.actions.entity.metadata.other;

import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.common.recordables.types.entity.metadata.other.RecArmorStandArms;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.other.ArmorStandMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.Map;

public record RecArmorStandArmsAction() implements EntityRecordableAction<RecArmorStandArms> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecArmorStandArms recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        EntityMetadata entityMetadata = data.entity().getMetadata();
        if (entityMetadata instanceof ArmorStandMetadata armorStandMetadata) {
            Metadata metadata = armorStandMetadata.getMetadata();

            metadata.detectChanges(true);

            armorStandMetadata.setHasArms(recordable.arms());

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