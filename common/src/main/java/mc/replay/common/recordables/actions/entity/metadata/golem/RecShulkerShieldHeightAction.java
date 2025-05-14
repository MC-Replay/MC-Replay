package mc.replay.common.recordables.actions.entity.metadata.golem;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.golem.RecShulkerShieldHeight;
import mc.replay.nms.entity.metadata.golem.ShulkerMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecShulkerShieldHeightAction implements InternalEntityMetadataRecordableAction<RecShulkerShieldHeight> {

    @Override
    public void writeMetadata(@NotNull RecShulkerShieldHeight recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof ShulkerMetadata shulkerMetadata) {
            shulkerMetadata.setShieldHeight(recordable.shieldHeight());
        }
    }
}