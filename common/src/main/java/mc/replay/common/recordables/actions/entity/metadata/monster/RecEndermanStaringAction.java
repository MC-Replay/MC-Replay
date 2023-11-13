package mc.replay.common.recordables.actions.entity.metadata.monster;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.monster.RecEndermanStaring;
import mc.replay.nms.entity.metadata.monster.EndermanMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecEndermanStaringAction implements InternalEntityMetadataRecordableAction<RecEndermanStaring> {

    @Override
    public void writeMetadata(@NotNull RecEndermanStaring recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof EndermanMetadata endermanMetadata) {
            endermanMetadata.setStaring(recordable.staring());
        }
    }
}