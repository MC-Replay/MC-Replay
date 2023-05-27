package mc.replay.common.recordables.actions.entity.metadata;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.RecEntitySprinting;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecEntitySprintingAction implements InternalEntityMetadataRecordableAction<RecEntitySprinting> {

    @Override
    public void writeMetadata(@NotNull RecEntitySprinting recordable, @NotNull EntityMetadata entityMetadata) {
        entityMetadata.setSprinting(recordable.sprinting());
    }
}