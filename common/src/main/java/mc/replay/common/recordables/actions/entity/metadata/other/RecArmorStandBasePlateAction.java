package mc.replay.common.recordables.actions.entity.metadata.other;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.other.RecArmorStandBasePlate;
import mc.replay.nms.entity.metadata.other.ArmorStandMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecArmorStandBasePlateAction implements InternalEntityMetadataRecordableAction<RecArmorStandBasePlate> {

    @Override
    public void writeMetadata(@NotNull RecArmorStandBasePlate recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof ArmorStandMetadata armorStandMetadata) {
            armorStandMetadata.setHasBasePlate(recordable.basePlate());
        }
    }
}