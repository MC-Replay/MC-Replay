package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecFoxSleeping;
import mc.replay.nms.entity.metadata.animal.FoxMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecFoxSleepingAction implements InternalEntityMetadataRecordableAction<RecFoxSleeping> {

    @Override
    public void writeMetadata(@NotNull RecFoxSleeping recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof FoxMetadata beeMetadata) {
            beeMetadata.setSleeping(recordable.sleeping());
        }
    }
}