package mc.replay.common.recordables.actions.entity.metadata.animal.tameable;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.tameable.RecWolfBegging;
import mc.replay.nms.entity.metadata.animal.tameable.WolfMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecWolfBeggingAction implements InternalEntityMetadataRecordableAction<RecWolfBegging> {

    @Override
    public void writeMetadata(@NotNull RecWolfBegging recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof WolfMetadata wolfMetadata) {
            wolfMetadata.setBegging(recordable.begging());
        }
    }
}