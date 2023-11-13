package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecAbstractHorseRearing;
import mc.replay.nms.entity.metadata.animal.AbstractHorseMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecAbstractHorseRearingAction implements InternalEntityMetadataRecordableAction<RecAbstractHorseRearing> {

    @Override
    public void writeMetadata(@NotNull RecAbstractHorseRearing recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof AbstractHorseMetadata abstractHorseMetadata) {
            abstractHorseMetadata.setRearing(recordable.rearing());
        }
    }
}