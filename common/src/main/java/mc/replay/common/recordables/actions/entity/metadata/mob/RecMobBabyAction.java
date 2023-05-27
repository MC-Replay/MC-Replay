package mc.replay.common.recordables.actions.entity.metadata.mob;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.mob.RecMobBaby;
import mc.replay.wrapper.entity.metadata.AgeableMobMetadata;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecMobBabyAction implements InternalEntityMetadataRecordableAction<RecMobBaby> {

    @Override
    public void writeMetadata(@NotNull RecMobBaby recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof AgeableMobMetadata ageableMobMetadata) {
            ageableMobMetadata.setBaby(recordable.baby());
        }
    }
}