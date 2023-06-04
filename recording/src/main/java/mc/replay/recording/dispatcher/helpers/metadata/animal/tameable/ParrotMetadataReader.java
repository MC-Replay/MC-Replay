package mc.replay.recording.dispatcher.helpers.metadata.animal.tameable;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.RecEntityVariant;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.animal.tameable.ParrotMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.animal.tameable.ParrotMetadata.COLOR_INDEX;

public final class ParrotMetadataReader implements MetadataReader<ParrotMetadata> {

    @Override
    public List<Recordable> read(ParrotMetadata before, ParrotMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(COLOR_INDEX) != null) {
            if (metadata.getColor() != before.getColor()) {
                recordables.add(
                        new RecEntityVariant(
                                entityId,
                                metadata.getColor().ordinal()
                        )
                );
            }
        }

        return recordables;
    }
}