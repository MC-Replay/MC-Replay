package mc.replay.common.recordables.actions.entity.metadata.living;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.living.RecLivingEntityArrowCount;
import mc.replay.nms.entity.metadata.LivingEntityMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecLivingEntityArrowCountAction implements InternalEntityMetadataRecordableAction<RecLivingEntityArrowCount> {

    @Override
    public void writeMetadata(@NotNull RecLivingEntityArrowCount recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof LivingEntityMetadata livingEntityMetadata) {
            livingEntityMetadata.setArrowCount(recordable.arrowCount());
        }
    }
}