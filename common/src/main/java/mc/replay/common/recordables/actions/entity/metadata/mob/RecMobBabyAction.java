package mc.replay.common.recordables.actions.entity.metadata.mob;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.mob.RecMobBaby;
import mc.replay.nms.entity.metadata.AgeableMobMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecMobBabyAction implements InternalEntityMetadataRecordableAction<RecMobBaby> {

    @Override
    public void writeMetadata(@NotNull RecMobBaby recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof AgeableMobMetadata ageableMobMetadata) {
            ageableMobMetadata.setBaby(recordable.baby());
        }
    }
}