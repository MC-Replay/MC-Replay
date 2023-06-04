package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecStriderSaddle;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.animal.StriderMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecStriderSaddleAction implements InternalEntityMetadataRecordableAction<RecStriderSaddle> {

    @Override
    public void writeMetadata(@NotNull RecStriderSaddle recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof StriderMetadata striderMetadata) {
            striderMetadata.setHasSaddle(recordable.saddle());
        }
    }
}