package mc.replay.common.recordables.actions.entity.metadata.animal.tameable;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.tameable.RecCatRelaxed;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.animal.tameable.CatMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecCatRelaxedAction implements InternalEntityMetadataRecordableAction<RecCatRelaxed> {

    @Override
    public void writeMetadata(@NotNull RecCatRelaxed recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof CatMetadata catMetadata) {
            catMetadata.setRelaxed(recordable.relaxed());
        }
    }
}