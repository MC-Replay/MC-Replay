package mc.replay.recording.dispatcher.helpers.metadata.other;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.RecEntityVariant;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.other.BoatMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.nms.entity.metadata.other.BoatMetadata.TYPE_INDEX;

public final class BoatMetadataReader implements MetadataReader<BoatMetadata> {

    @Override
    public List<Recordable> read(BoatMetadata before, BoatMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(TYPE_INDEX) != null) {
            if (metadata.getType() != before.getType()) {
                recordables.add(
                        new RecEntityVariant(
                                entityId,
                                metadata.getType().ordinal()
                        )
                );
            }
        }

        return recordables;
    }
}