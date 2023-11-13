package mc.replay.common.recordables.actions.entity.metadata;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.RecEntitySwimming;
import mc.replay.nms.entity.metadata.EntityMetadata;
import org.bukkit.entity.Pose;
import org.jetbrains.annotations.NotNull;

public final class RecEntitySwimmingAction implements InternalEntityMetadataRecordableAction<RecEntitySwimming> {

    @Override
    public void writeMetadata(@NotNull RecEntitySwimming recordable, @NotNull RMetadata metadata) {
        if (metadata instanceof EntityMetadata entityMetadata) {
            entityMetadata.setSwimming(recordable.swimming());
            entityMetadata.setPose(recordable.swimming() ? Pose.SWIMMING : Pose.STANDING);
        }
    }
}