package mc.replay.common.recordables.actions.entity.metadata.other;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.other.RecArmorStandArms;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.other.ArmorStandMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecArmorStandArmsAction implements InternalEntityMetadataRecordableAction<RecArmorStandArms> {

    @Override
    public void writeMetadata(@NotNull RecArmorStandArms recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof ArmorStandMetadata armorStandMetadata) {
            armorStandMetadata.setHasArms(recordable.arms());
        }
    }
}