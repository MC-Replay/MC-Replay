package mc.replay.common.recordables.actions.entity.metadata.flying;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.flying.RecPhantomSize;
import mc.replay.nms.entity.metadata.flying.PhantomMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecPhantomSizeAction implements InternalEntityMetadataRecordableAction<RecPhantomSize> {

    @Override
    public void writeMetadata(@NotNull RecPhantomSize recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof PhantomMetadata phantomMetadata) {
            phantomMetadata.setSize(recordable.size());
        }
    }
}