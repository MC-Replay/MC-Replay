package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecStriderShaking;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.animal.StriderMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecStriderShakingAction implements InternalEntityMetadataRecordableAction<RecStriderShaking> {

    @Override
    public void writeMetadata(@NotNull RecStriderShaking recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof StriderMetadata striderMetadata) {
            striderMetadata.setShaking(recordable.shaking());
        }
    }
}