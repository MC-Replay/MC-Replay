package mc.replay.recording.dispatcher.helpers.metadata.animal;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.animal.RecBeeNectar;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.animal.BeeMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.animal.BeeMetadata.MASK_INDEX;

public final class BeeMetadataReader implements MetadataReader<BeeMetadata> {

    @Override
    public List<Recordable> read(BeeMetadata before, BeeMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(MASK_INDEX) != null) {
            if (metadata.hasNectar() != before.hasNectar()) {
                recordables.add(
                        new RecBeeNectar(
                                entityId,
                                metadata.hasNectar()
                        )
                );
            }
        }

        return recordables;
    }
}