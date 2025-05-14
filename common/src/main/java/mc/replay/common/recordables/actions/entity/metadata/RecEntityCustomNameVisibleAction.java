package mc.replay.common.recordables.actions.entity.metadata;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.RecEntityCustomNameVisible;
import org.jetbrains.annotations.NotNull;

public final class RecEntityCustomNameVisibleAction implements InternalEntityMetadataRecordableAction<RecEntityCustomNameVisible> {

    @Override
    public void writeMetadata(@NotNull RecEntityCustomNameVisible recordable, @NotNull EntityMetadata entityMetadata) {
        entityMetadata.setCustomNameVisible(recordable.visible());
    }
}