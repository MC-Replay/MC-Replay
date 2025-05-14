package mc.replay.common.recordables.actions.entity.metadata;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.RecEntityPose;
import org.jetbrains.annotations.NotNull;

public final class RecEntityPoseAction implements InternalEntityMetadataRecordableAction<RecEntityPose> {

    @Override
    public void writeMetadata(@NotNull RecEntityPose recordable, @NotNull EntityMetadata entityMetadata) {
        entityMetadata.setPose(recordable.pose());
    }
}