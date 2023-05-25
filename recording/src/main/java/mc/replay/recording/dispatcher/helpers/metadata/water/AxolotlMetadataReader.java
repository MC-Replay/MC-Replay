package mc.replay.recording.dispatcher.helpers.metadata.water;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.RecEntityVariant;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.water.AxolotlMetadata;

import java.util.*;

import static mc.replay.wrapper.entity.metadata.water.AxolotlMetadata.*;

public final class AxolotlMetadataReader implements MetadataReader<AxolotlMetadata> {

    @Override
    public List<Recordable> read(AxolotlMetadata before, AxolotlMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(VARIANT_INDEX) != null) {
            if (before.getVariant() != metadata.getVariant()) {
                recordables.add(
                        new RecEntityVariant(
                                entityId,
                                metadata.getVariant().ordinal()
                        )
                );
            }
        }

        return recordables;
    }

    @Override
    public Collection<Integer> skippedIndexes() {
        return Set.of(FROM_BUCKET_INDEX, PLAYING_DEAD_INDEX);
    }
}