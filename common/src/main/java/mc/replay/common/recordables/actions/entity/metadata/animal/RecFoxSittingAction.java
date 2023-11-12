package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecFoxSitting;
import mc.replay.nms.entity.metadata.animal.FoxMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecFoxSittingAction implements InternalEntityMetadataRecordableAction<RecFoxSitting> {

    @Override
    public void writeMetadata(@NotNull RecFoxSitting recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof FoxMetadata beeMetadata) {
            beeMetadata.setSitting(recordable.sitting());
        }
    }
}