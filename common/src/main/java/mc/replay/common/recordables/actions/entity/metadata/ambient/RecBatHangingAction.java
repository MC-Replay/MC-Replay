package mc.replay.common.recordables.actions.entity.metadata.ambient;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.ambient.RecBatHanging;
import mc.replay.nms.entity.metadata.ambient.BatMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecBatHangingAction implements InternalEntityMetadataRecordableAction<RecBatHanging> {

    @Override
    public void writeMetadata(@NotNull RecBatHanging recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof BatMetadata batMetadata) {
            batMetadata.setHanging(recordable.hanging());
        }
    }
}