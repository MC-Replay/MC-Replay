package mc.replay.common.recordables.actions.entity.metadata.monster;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.monster.RecPiglinDancing;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.monster.PiglinMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecPiglinDancingAction implements InternalEntityMetadataRecordableAction<RecPiglinDancing> {

    @Override
    public void writeMetadata(@NotNull RecPiglinDancing recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof PiglinMetadata piglinMetadata) {
            piglinMetadata.setDancing(recordable.dancing());
        }
    }
}