package mc.replay.common.recordables.actions.entity.metadata.mob;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.mob.RecMobLeftHanded;
import mc.replay.nms.entity.metadata.MobMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecMobLeftHandedAction implements InternalEntityMetadataRecordableAction<RecMobLeftHanded> {

    @Override
    public void writeMetadata(@NotNull RecMobLeftHanded recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof MobMetadata mobMetadata) {
            mobMetadata.setLeftHanded(recordable.leftHanded());
        }
    }
}