package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecAbstractHorseEating;
import mc.replay.nms.entity.metadata.animal.AbstractHorseMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecAbstractHorseEatingAction implements InternalEntityMetadataRecordableAction<RecAbstractHorseEating> {

    @Override
    public void writeMetadata(@NotNull RecAbstractHorseEating recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof AbstractHorseMetadata abstractHorseMetadata) {
            abstractHorseMetadata.setEating(recordable.eating());
        }
    }
}