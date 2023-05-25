package mc.replay.recording.dispatcher.helpers.metadata.other;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.other.RecItemFrameItem;
import mc.replay.common.recordables.types.entity.metadata.other.RecItemFrameRotation;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.other.ItemFrameMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.other.ItemFrameMetadata.ITEM_INDEX;
import static mc.replay.wrapper.entity.metadata.other.ItemFrameMetadata.ROTATION_INDEX;

public final class ItemFrameMetadataReader implements MetadataReader<ItemFrameMetadata> {

    @Override
    public List<Recordable> read(ItemFrameMetadata before, ItemFrameMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(ITEM_INDEX) != null) {
            if (metadata.getItem() != before.getItem()) {
                recordables.add(
                        new RecItemFrameItem(
                                entityId,
                                metadata.getItem()
                        )
                );
            }
        }

        if (entries.remove(ROTATION_INDEX) != null) {
            if (metadata.getRotation() != before.getRotation()) {
                recordables.add(
                        new RecItemFrameRotation(
                                entityId,
                                metadata.getRotation()
                        )
                );
            }
        }

        return recordables;
    }
}