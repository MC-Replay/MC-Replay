package mc.replay.recording.dispatcher.helpers.metadata.animal;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.animal.RecGoatScreaming;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.animal.GoatMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.animal.GoatMetadata.SCREAMING_INDEX;

public final class GoatMetadataReader implements MetadataReader<GoatMetadata> {

    @Override
    public List<Recordable> read(GoatMetadata before, GoatMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(SCREAMING_INDEX) != null) {
            if (metadata.isScreaming() != before.isScreaming()) {
                recordables.add(
                        new RecGoatScreaming(
                                entityId,
                                metadata.isScreaming()
                        )
                );
            }
        }

        return recordables;
    }
}