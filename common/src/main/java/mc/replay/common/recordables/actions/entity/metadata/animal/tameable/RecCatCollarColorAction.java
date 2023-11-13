package mc.replay.common.recordables.actions.entity.metadata.animal.tameable;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.tameable.RecCatCollarColor;
import mc.replay.nms.entity.metadata.animal.tameable.CatMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecCatCollarColorAction implements InternalEntityMetadataRecordableAction<RecCatCollarColor> {

    @Override
    public void writeMetadata(@NotNull RecCatCollarColor recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof CatMetadata catMetadata) {
            catMetadata.setCollarColor(recordable.collarColor());
        }
    }
}