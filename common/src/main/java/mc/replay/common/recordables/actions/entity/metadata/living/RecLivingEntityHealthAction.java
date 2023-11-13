package mc.replay.common.recordables.actions.entity.metadata.living;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.living.RecLivingEntityHealth;
import mc.replay.nms.entity.metadata.LivingEntityMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecLivingEntityHealthAction implements InternalEntityMetadataRecordableAction<RecLivingEntityHealth> {

    @Override
    public void writeMetadata(@NotNull RecLivingEntityHealth recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof LivingEntityMetadata livingEntityMetadata) {
            livingEntityMetadata.setHealth(recordable.health());
        }
    }
}