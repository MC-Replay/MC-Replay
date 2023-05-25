package mc.replay.common.recordables.actions.entity.metadata.other;

import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.common.recordables.types.entity.metadata.other.RecArmorStandRotation;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.other.ArmorStandMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.Map;

public record RecArmorStandRotationAction() implements EntityRecordableAction<RecArmorStandRotation> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecArmorStandRotation recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        EntityMetadata entityMetadata = data.entity().getMetadata();
        if (entityMetadata instanceof ArmorStandMetadata armorStandMetadata) {
            Metadata metadata = armorStandMetadata.getMetadata();

            metadata.detectChanges(true);

            switch (recordable.type()) {
                case HEAD -> armorStandMetadata.setHeadRotation(recordable.rotation());
                case BODY -> armorStandMetadata.setBodyRotation(recordable.rotation());
                case LEFT_ARM -> armorStandMetadata.setLeftArmRotation(recordable.rotation());
                case RIGHT_ARM -> armorStandMetadata.setRightArmRotation(recordable.rotation());
                case LEFT_LEG -> armorStandMetadata.setLeftLegRotation(recordable.rotation());
                case RIGHT_LEG -> armorStandMetadata.setRightLegRotation(recordable.rotation());
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