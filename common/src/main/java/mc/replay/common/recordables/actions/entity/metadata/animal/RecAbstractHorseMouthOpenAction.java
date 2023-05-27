package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecAbstractHorseMouthOpen;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.animal.AbstractHorseMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecAbstractHorseMouthOpenAction implements InternalEntityMetadataRecordableAction<RecAbstractHorseMouthOpen> {

    @Override
    public void writeMetadata(@NotNull RecAbstractHorseMouthOpen recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof AbstractHorseMetadata abstractHorseMetadata) {
            abstractHorseMetadata.setMouthOpen(recordable.mouthOpen());
        }
    }
}