package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecSheepSheared;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.animal.SheepMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecSheepShearedAction implements InternalEntityMetadataRecordableAction<RecSheepSheared> {

    @Override
    public void writeMetadata(@NotNull RecSheepSheared recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof SheepMetadata sheepMetadata) {
            sheepMetadata.setSheared(recordable.sheared());
        }
    }
}