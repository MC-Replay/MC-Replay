package mc.replay.recording.dispatcher.helpers.metadata.ambient;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.ambient.RecBatHanging;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.ambient.BatMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.ambient.BatMetadata.MASK_BIT;

public final class BatMetadataReader implements MetadataReader<BatMetadata> {

    @Override
    public List<Recordable> read(BatMetadata before, BatMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(MASK_BIT) != null) {
            if (metadata.isHanging() != before.isHanging()) {
                recordables.add(
                        new RecBatHanging(
                                entityId,
                                metadata.isHanging()
                        )
                );
            }
        }

        return recordables;
    }
}