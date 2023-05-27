package mc.replay.common.recordables.actions.entity.metadata.other;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.other.RecSlimeSize;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.other.SlimeMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecSlimeSizeAction implements InternalEntityMetadataRecordableAction<RecSlimeSize> {

    @Override
    public void writeMetadata(@NotNull RecSlimeSize recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof SlimeMetadata slimeMetadata) {
            slimeMetadata.setSize(recordable.size());
        }
    }
}