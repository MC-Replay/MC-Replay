package mc.replay.recording.dispatcher.helpers.metadata.animal.tameable;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.animal.tameable.RecTameableAnimalSitting;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.animal.tameable.TameableAnimalMetadata;

import java.util.*;

import static mc.replay.wrapper.entity.metadata.animal.tameable.TameableAnimalMetadata.MASK_INDEX;
import static mc.replay.wrapper.entity.metadata.animal.tameable.TameableAnimalMetadata.OWNER_INDEX;

public final class TameableAnimalMetadataReader implements MetadataReader<TameableAnimalMetadata> {

    @Override
    public List<Recordable> read(TameableAnimalMetadata before, TameableAnimalMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(MASK_INDEX) != null) {
            if (metadata.isSitting() != before.isSitting()) {
                recordables.add(
                        new RecTameableAnimalSitting(
                                entityId,
                                metadata.isSitting()
                        )
                );
            }
        }

        return recordables;
    }

    @Override
    public Collection<Integer> skippedIndexes() {
        return Set.of(OWNER_INDEX);
    }
}