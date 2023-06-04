package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecPigSaddle;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.animal.PigMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecPigSaddleAction implements InternalEntityMetadataRecordableAction<RecPigSaddle> {

    @Override
    public void writeMetadata(@NotNull RecPigSaddle recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof PigMetadata pigMetadata) {
            pigMetadata.setHasSaddle(recordable.saddle());
        }
    }
}