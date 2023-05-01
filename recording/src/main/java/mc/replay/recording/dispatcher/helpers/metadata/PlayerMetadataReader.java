package mc.replay.recording.dispatcher.helpers.metadata;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.wrapper.entity.metadata.PlayerMetadata;

import java.util.*;

import static mc.replay.wrapper.entity.metadata.PlayerMetadata.*;

public final class PlayerMetadataReader implements MetadataReader<PlayerMetadata> {

    @Override
    public List<Recordable> read(PlayerMetadata before, PlayerMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();


        return recordables;
    }

    @Override
    public Collection<Integer> skippedIndexes() {
        return Set.of(ADDITIONAL_HEARTS_INDEX, SCORE_INDEX, DISPLAYED_SKIN_PARTS_INDEX);
    }
}