package mc.replay.recording.dispatcher.helpers.metadata.golem;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.golem.RecShulkerShieldHeight;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.golem.ShulkerMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.nms.entity.metadata.golem.ShulkerMetadata.SHIELD_HEIGHT_INDEX;

public final class ShulkerMetadataReader implements MetadataReader<ShulkerMetadata> {

    @Override
    public List<Recordable> read(ShulkerMetadata before, ShulkerMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(SHIELD_HEIGHT_INDEX) != null) {
            if (metadata.getShieldHeight() != before.getShieldHeight()) {
                recordables.add(
                        new RecShulkerShieldHeight(
                                entityId,
                                metadata.getShieldHeight()
                        )
                );
            }
        }

        return recordables;
    }
}