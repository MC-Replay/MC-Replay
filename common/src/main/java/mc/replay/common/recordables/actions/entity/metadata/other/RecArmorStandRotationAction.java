package mc.replay.common.recordables.actions.entity.metadata.other;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.other.RecArmorStandRotation;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.other.ArmorStandMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecArmorStandRotationAction implements InternalEntityMetadataRecordableAction<RecArmorStandRotation> {

    @Override
    public void writeMetadata(@NotNull RecArmorStandRotation recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof ArmorStandMetadata armorStandMetadata) {
            switch (recordable.type()) {
                case HEAD -> armorStandMetadata.setHeadRotation(recordable.rotation());
                case BODY -> armorStandMetadata.setBodyRotation(recordable.rotation());
                case LEFT_ARM -> armorStandMetadata.setLeftArmRotation(recordable.rotation());
                case RIGHT_ARM -> armorStandMetadata.setRightArmRotation(recordable.rotation());
                case LEFT_LEG -> armorStandMetadata.setLeftLegRotation(recordable.rotation());
                case RIGHT_LEG -> armorStandMetadata.setRightLegRotation(recordable.rotation());
            }
        }
    }
}