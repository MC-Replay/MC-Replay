package mc.replay.common.recordables.actions.entity.metadata;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.RecEntityInvisible;
import mc.replay.nms.entity.metadata.EntityMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecEntityInvisibleAction implements InternalEntityMetadataRecordableAction<RecEntityInvisible> {

    @Override
    public void writeMetadata(@NotNull RecEntityInvisible recordable, @NotNull RMetadata metadata) {
        if (metadata instanceof EntityMetadata entityMetadata) {
            entityMetadata.setInvisible(recordable.invisible());
        }
    }
}