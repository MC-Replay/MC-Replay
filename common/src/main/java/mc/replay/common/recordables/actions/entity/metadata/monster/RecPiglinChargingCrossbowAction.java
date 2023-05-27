package mc.replay.common.recordables.actions.entity.metadata.monster;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.monster.RecPiglinChargingCrossbow;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.monster.PiglinMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecPiglinChargingCrossbowAction implements InternalEntityMetadataRecordableAction<RecPiglinChargingCrossbow> {

    @Override
    public void writeMetadata(@NotNull RecPiglinChargingCrossbow recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof PiglinMetadata piglinMetadata) {
            piglinMetadata.setChargingCrossbow(recordable.charging());
        }
    }
}