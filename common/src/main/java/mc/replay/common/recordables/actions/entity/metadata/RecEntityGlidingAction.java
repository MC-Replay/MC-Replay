package mc.replay.common.recordables.actions.entity.metadata;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.RecEntityGliding;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecEntityGlidingAction implements InternalEntityMetadataRecordableAction<RecEntityGliding> {

    @Override
    public void writeMetadata(@NotNull RecEntityGliding recordable, @NotNull EntityMetadata entityMetadata) {
        entityMetadata.setIsFlyingWithElytra(recordable.gliding());
    }
}