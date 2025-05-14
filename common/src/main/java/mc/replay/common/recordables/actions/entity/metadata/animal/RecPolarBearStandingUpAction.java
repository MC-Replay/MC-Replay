package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecPolarBearStandingUp;
import mc.replay.nms.entity.metadata.animal.PolarBearMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecPolarBearStandingUpAction implements InternalEntityMetadataRecordableAction<RecPolarBearStandingUp> {

    @Override
    public void writeMetadata(@NotNull RecPolarBearStandingUp recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof PolarBearMetadata polarBearMetadata) {
            polarBearMetadata.setStandingUp(recordable.standingUp());
        }
    }
}