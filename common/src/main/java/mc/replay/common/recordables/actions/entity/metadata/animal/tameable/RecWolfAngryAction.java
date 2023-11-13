package mc.replay.common.recordables.actions.entity.metadata.animal.tameable;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.tameable.RecWolfAngry;
import mc.replay.nms.entity.metadata.animal.tameable.WolfMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecWolfAngryAction implements InternalEntityMetadataRecordableAction<RecWolfAngry> {

    @Override
    public void writeMetadata(@NotNull RecWolfAngry recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof WolfMetadata wolfMetadata) {
            wolfMetadata.setAngerTime((recordable.angry()) ? 1 : 0);
        }
    }
}