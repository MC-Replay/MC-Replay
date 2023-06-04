package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecTurtleEgg;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.animal.TurtleMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecTurtleEggAction implements InternalEntityMetadataRecordableAction<RecTurtleEgg> {

    @Override
    public void writeMetadata(@NotNull RecTurtleEgg recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof TurtleMetadata turtleMetadata) {
            turtleMetadata.setHasEgg(recordable.egg());
        }
    }
}