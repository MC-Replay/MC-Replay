package mc.replay.common.recordables.actions.entity.metadata.golem;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.golem.RecSnowGolemPumpkinHat;
import mc.replay.nms.entity.metadata.golem.SnowGolemMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecSnowGolemPumpkinHatAction implements InternalEntityMetadataRecordableAction<RecSnowGolemPumpkinHat> {

    @Override
    public void writeMetadata(@NotNull RecSnowGolemPumpkinHat recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof SnowGolemMetadata snowGolemMetadata) {
            snowGolemMetadata.setHasPumpkinHat(recordable.pumpkinHat());
        }
    }
}