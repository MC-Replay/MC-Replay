package mc.replay.common.recordables.actions.entity.metadata;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.RecEntityGlowing;
import org.jetbrains.annotations.NotNull;

public final class RecEntityGlowingAction implements InternalEntityMetadataRecordableAction<RecEntityGlowing> {

    @Override
    public void writeMetadata(@NotNull RecEntityGlowing recordable, @NotNull EntityMetadata entityMetadata) {
        entityMetadata.setHasGlowingEffect(recordable.glowing());
    }
}