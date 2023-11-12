package mc.replay.common.recordables.actions.entity.metadata.animal.tameable;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.tameable.RecCatLying;
import mc.replay.nms.entity.metadata.animal.tameable.CatMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecCatLyingAction implements InternalEntityMetadataRecordableAction<RecCatLying> {

    @Override
    public void writeMetadata(@NotNull RecCatLying recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof CatMetadata catMetadata) {
            catMetadata.setLying(recordable.lying());
        }
    }
}