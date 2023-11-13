package mc.replay.recording.dispatcher.helpers.metadata;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.data.entity.Metadata;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MetadataReader<M extends RMetadata> {

    List<Recordable> read(M before, M metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId);

    default Collection<Integer> skippedIndexes() {
        return Set.of();
    }
}