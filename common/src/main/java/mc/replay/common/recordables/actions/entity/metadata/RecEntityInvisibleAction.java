package mc.replay.common.recordables.actions.entity.metadata;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.RecEntityInvisible;
import org.jetbrains.annotations.NotNull;

public final class RecEntityInvisibleAction implements InternalEntityMetadataRecordableAction<RecEntityInvisible> {

    @Override
    public void writeMetadata(@NotNull RecEntityInvisible recordable, @NotNull EntityMetadata entityMetadata) {
        entityMetadata.setInvisible(recordable.invisible());
    }
}