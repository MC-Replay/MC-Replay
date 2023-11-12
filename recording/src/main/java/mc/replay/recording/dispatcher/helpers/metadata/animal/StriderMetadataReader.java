package mc.replay.recording.dispatcher.helpers.metadata.animal;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.animal.RecStriderSaddle;
import mc.replay.common.recordables.types.entity.metadata.animal.RecStriderShaking;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.animal.StriderMetadata;

import java.util.*;

import static mc.replay.nms.entity.metadata.animal.StriderMetadata.*;

public final class StriderMetadataReader implements MetadataReader<StriderMetadata> {

    @Override
    public List<Recordable> read(StriderMetadata before, StriderMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(SHAKING_INDEX) != null) {
            if (metadata.isShaking() != before.isShaking()) {
                recordables.add(
                        new RecStriderShaking(
                                entityId,
                                metadata.isShaking()
                        )
                );
            }
        }

        if (entries.remove(HAS_SADDLE_INDEX) != null) {
            if (metadata.hasSaddle() != before.hasSaddle()) {
                recordables.add(
                        new RecStriderSaddle(
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