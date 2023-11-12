package mc.replay.recording.dispatcher.helpers.metadata.animal;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.RecEntityVariant;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.animal.RabbitMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.nms.entity.metadata.animal.RabbitMetadata.TYPE_INDEX;

public final class RabbitMetadataReader implements MetadataReader<RabbitMetadata> {

    @Override
    public List<Recordable> read(RabbitMetadata before, RabbitMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
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