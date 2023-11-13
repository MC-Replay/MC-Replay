package mc.replay.common.recordables.actions.entity.metadata.other;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.other.RecItemFrameItem;
import mc.replay.nms.entity.metadata.other.ItemFrameMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecItemFrameItemAction implements InternalEntityMetadataRecordableAction<RecItemFrameItem> {

    @Override
    public void writeMetadata(@NotNull RecItemFrameItem recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof ItemFrameMetadata itemFrameMetadata) {
            itemFrameMetadata.setItem(recordable.item());
        }
    }
}