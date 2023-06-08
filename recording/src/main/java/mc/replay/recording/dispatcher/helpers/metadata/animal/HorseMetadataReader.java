package mc.replay.recording.dispatcher.helpers.metadata.animal;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.RecEntityVariant;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.animal.HorseMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static mc.replay.wrapper.entity.metadata.animal.HorseMetadata.VARIANT_INDEX;

public final class HorseMetadataReader implements MetadataReader<HorseMetadata> {

    @Override
    public List<Recordable> read(HorseMetadata before, HorseMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(VARIANT_INDEX) != null) {
            HorseMetadata.Variant variant = metadata.getVariant();

            if (!Objects.equals(variant, before.getVariant())) {
                recordables.add(
                        new RecEntityVariant(
                                entityId,
                                HorseMetadata.getVariantId(variant.getMarking(), variant.getColor())
                        )
                );
            }
        }

        return recordables;
    }
}