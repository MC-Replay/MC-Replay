package mc.replay.common.recordables.actions.entity.metadata;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.RecEntitySneaking;
import mc.replay.nms.entity.metadata.EntityMetadata;
import org.bukkit.entity.Pose;
import org.jetbrains.annotations.NotNull;

public final class RecEntitySneakingAction implements InternalEntityMetadataRecordableAction<RecEntitySneaking> {

    @Override
    public void writeMetadata(@NotNull RecEntitySneaking recordable, @NotNull RMetadata metadata) {
        if (metadata instanceof EntityMetadata entityMetadata) {
            entityMetadata.setSneaking(recordable.sneaking());
            entityMetadata.setPose(recordable.sneaking() ? Pose.SNEAKING : Pose.STANDING);
        }
    }
}