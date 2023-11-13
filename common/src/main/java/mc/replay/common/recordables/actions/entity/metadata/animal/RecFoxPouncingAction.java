package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecFoxPouncing;
import mc.replay.nms.entity.metadata.animal.FoxMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecFoxPouncingAction implements InternalEntityMetadataRecordableAction<RecFoxPouncing> {

    @Override
    public void writeMetadata(@NotNull RecFoxPouncing recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof FoxMetadata beeMetadata) {
            beeMetadata.setPouncing(recordable.pouncing());
        }
    }
}