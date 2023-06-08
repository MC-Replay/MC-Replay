package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecPandaRolling;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.animal.PandaMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecPandaRollingAction implements InternalEntityMetadataRecordableAction<RecPandaRolling> {

    @Override
    public void writeMetadata(@NotNull RecPandaRolling recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof PandaMetadata pandaMetadata) {
            pandaMetadata.setRolling(recordable.rolling());
        }
    }
}