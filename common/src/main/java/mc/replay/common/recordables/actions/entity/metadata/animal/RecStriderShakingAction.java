package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecStriderShaking;
import mc.replay.nms.entity.metadata.animal.StriderMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecStriderShakingAction implements InternalEntityMetadataRecordableAction<RecStriderShaking> {

    @Override
    public void writeMetadata(@NotNull RecStriderShaking recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof StriderMetadata striderMetadata) {
            striderMetadata.setShaking(recordable.shaking());
        }
    }
}