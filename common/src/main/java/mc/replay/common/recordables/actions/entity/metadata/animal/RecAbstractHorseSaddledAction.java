package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecAbstractHorseSaddled;
import mc.replay.nms.entity.metadata.animal.AbstractHorseMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecAbstractHorseSaddledAction implements InternalEntityMetadataRecordableAction<RecAbstractHorseSaddled> {

    @Override
    public void writeMetadata(@NotNull RecAbstractHorseSaddled recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof AbstractHorseMetadata abstractHorseMetadata) {
            abstractHorseMetadata.setSaddled(recordable.saddled());
        }
    }
}