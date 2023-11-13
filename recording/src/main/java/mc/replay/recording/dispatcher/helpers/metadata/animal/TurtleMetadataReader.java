package mc.replay.recording.dispatcher.helpers.metadata.animal;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.animal.RecTurtleEgg;
import mc.replay.common.recordables.types.entity.metadata.animal.RecTurtleLayingEgg;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.animal.TurtleMetadata;

import java.util.*;

import static mc.replay.nms.entity.metadata.animal.TurtleMetadata.*;

public final class TurtleMetadataReader implements MetadataReader<TurtleMetadata> {

    @Override
    public List<Recordable> read(TurtleMetadata before, TurtleMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(HAS_EGG_INDEX) != null) {
            if (metadata.isHasEgg() != before.isHasEgg()) {
                recordables.add(
                        new RecTurtleEgg(
                                entityId,
                                metadata.isHasEgg()
                        )
                );
            }
        }

        if (entries.remove(LAYING_EGG_INDEX) != null) {
            if (metadata.isLayingEgg() != before.isLayingEgg()) {
                recordables.add(
                        new RecTurtleLayingEgg(
                                entityId,
                                metadata.isLayingEgg()
                        )
                );
            }
        }

        return recordables;
    }

    @Override
    public Collection<Integer> skippedIndexes() {
        return Set.of(HOME_POSITION_INDEX, TRAVEL_POSITION_INDEX, GOING_HOME_INDEX, TRAVELLING_INDEX);
    }
}