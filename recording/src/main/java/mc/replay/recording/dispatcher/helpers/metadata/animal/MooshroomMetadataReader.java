package mc.replay.recording.dispatcher.helpers.metadata.animal;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.RecEntityVariant;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.animal.MooshroomMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.animal.MooshroomMetadata.VARIANT_INDEX;

public final class MooshroomMetadataReader implements MetadataReader<MooshroomMetadata> {

    @Override
    public List<Recordable> read(MooshroomMetadata before, MooshroomMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(VARIANT_INDEX) != null) {
            if (metadata.getVariant() != before.getVariant()) {
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
}