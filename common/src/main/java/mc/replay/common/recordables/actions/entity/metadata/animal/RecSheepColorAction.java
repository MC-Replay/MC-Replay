package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecSheepColor;
import mc.replay.nms.entity.metadata.animal.SheepMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecSheepColorAction implements InternalEntityMetadataRecordableAction<RecSheepColor> {

    @Override
    public void writeMetadata(@NotNull RecSheepColor recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof SheepMetadata sheepMetadata) {
            sheepMetadata.setColor(recordable.color());
        }
    }
}