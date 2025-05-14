package mc.replay.common.recordables.actions.entity.metadata.monster;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.monster.RecPiglinChargingCrossbow;
import mc.replay.nms.entity.metadata.monster.PiglinMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecPiglinChargingCrossbowAction implements InternalEntityMetadataRecordableAction<RecPiglinChargingCrossbow> {

    @Override
    public void writeMetadata(@NotNull RecPiglinChargingCrossbow recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof PiglinMetadata piglinMetadata) {
            piglinMetadata.setChargingCrossbow(recordable.charging());
        }
    }
}