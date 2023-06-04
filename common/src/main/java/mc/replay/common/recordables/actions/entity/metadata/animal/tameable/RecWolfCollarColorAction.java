package mc.replay.common.recordables.actions.entity.metadata.animal.tameable;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.tameable.RecWolfCollarColor;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.animal.tameable.WolfMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecWolfCollarColorAction implements InternalEntityMetadataRecordableAction<RecWolfCollarColor> {

    @Override
    public void writeMetadata(@NotNull RecWolfCollarColor recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof WolfMetadata wolfMetadata) {
            wolfMetadata.setCollarColor(recordable.collarColor());
        }
    }
}