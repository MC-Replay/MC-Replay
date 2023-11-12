package mc.replay.recording.dispatcher.helpers.metadata.animal;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.animal.RecPigSaddle;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.animal.PigMetadata;

import java.util.*;

import static mc.replay.nms.entity.metadata.animal.PigMetadata.HAS_SADDLE_INDEX;
import static mc.replay.nms.entity.metadata.animal.PigMetadata.TIME_TO_BOOST_INDEX;

public final class PigMetadataReader implements MetadataReader<PigMetadata> {

    @Override
    public List<Recordable> read(PigMetadata before, PigMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(HAS_SADDLE_INDEX) != null) {
            if (metadata.hasSaddle() != before.hasSaddle()) {
                recordables.add(
                        new RecPigSaddle(
                                entityId,
                                metadata.hasSaddle()
                        )
                );
            }
        }

        return recordables;
    }

    @Override
    public Collection<Integer> skippedIndexes() {
        return Set.of(TIME_TO_BOOST_INDEX);
    }
}