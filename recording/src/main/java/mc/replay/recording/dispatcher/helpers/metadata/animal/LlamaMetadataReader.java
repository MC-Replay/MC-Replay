package mc.replay.recording.dispatcher.helpers.metadata.animal;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.RecEntityVariant;
import mc.replay.common.recordables.types.entity.metadata.animal.RecLlamaCarpetColor;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.animal.LlamaMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.nms.entity.metadata.animal.LlamaMetadata.CARPET_COLOR_INDEX;
import static mc.replay.nms.entity.metadata.animal.LlamaMetadata.VARIANT_INDEX;

public final class LlamaMetadataReader implements MetadataReader<LlamaMetadata> {

    @Override
    public List<Recordable> read(LlamaMetadata before, LlamaMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(CARPET_COLOR_INDEX) != null) {
            if (metadata.getCarpetColor() != before.getCarpetColor()) {
                recordables.add(
                        new RecLlamaCarpetColor(
                                entityId,
                                metadata.getCarpetColor()
                        )
                );
            }
        }

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