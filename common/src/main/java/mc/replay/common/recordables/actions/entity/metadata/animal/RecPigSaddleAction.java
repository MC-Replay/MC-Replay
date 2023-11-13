package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecPigSaddle;
import mc.replay.nms.entity.metadata.animal.PigMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecPigSaddleAction implements InternalEntityMetadataRecordableAction<RecPigSaddle> {

    @Override
    public void writeMetadata(@NotNull RecPigSaddle recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof PigMetadata pigMetadata) {
            pigMetadata.setHasSaddle(recordable.saddle());
        }
    }
}