package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecGoatScreaming;
import mc.replay.nms.entity.metadata.animal.GoatMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecGoatScreamingAction implements InternalEntityMetadataRecordableAction<RecGoatScreaming> {

    @Override
    public void writeMetadata(@NotNull RecGoatScreaming recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof GoatMetadata goatMetadata) {
            goatMetadata.setScreaming(recordable.screaming());
        }
    }
}