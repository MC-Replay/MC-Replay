package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecPandaEating;
import mc.replay.nms.entity.metadata.animal.PandaMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecPandaEatingAction implements InternalEntityMetadataRecordableAction<RecPandaEating> {

    @Override
    public void writeMetadata(@NotNull RecPandaEating recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof PandaMetadata pandaMetadata) {
            pandaMetadata.setEatTimer((recordable.eating()) ? 1 : 0);
        }
    }
}