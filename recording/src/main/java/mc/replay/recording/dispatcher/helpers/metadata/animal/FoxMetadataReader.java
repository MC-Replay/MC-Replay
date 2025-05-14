package mc.replay.recording.dispatcher.helpers.metadata.animal;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.RecEntityVariant;
import mc.replay.common.recordables.types.entity.metadata.animal.*;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.animal.FoxMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.nms.entity.metadata.animal.FoxMetadata.MASK_INDEX;
import static mc.replay.nms.entity.metadata.animal.FoxMetadata.TYPE_INDEX;

public final class FoxMetadataReader implements MetadataReader<FoxMetadata> {

    @Override
    public List<Recordable> read(FoxMetadata before, FoxMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(TYPE_INDEX) != null) {
            if (metadata.getType() != before.getType()) {
                recordables.add(
                        new RecEntityVariant(
                                entityId,
                                metadata.getType().ordinal()
                        )
                );
            }
        }

        if (entries.remove(MASK_INDEX) != null) {
            if (metadata.isSitting() != before.isSitting()) {
                recordables.add(
                        new RecFoxSitting(
                                entityId,
                                metadata.isSitting()
                        )
                );
            }

            if (metadata.isFoxSneaking() != before.isFoxSneaking()) {
                recordables.add(
                        new RecFoxSneaking(
                                entityId,
                                metadata.isFoxSneaking()
                        )
                );
            }

            if (metadata.isPouncing() != before.isPouncing()) {
                recordables.add(
                        new RecFoxPouncing(
                                entityId,
                                metadata.isPouncing()
                        )
                );
            }

            if (metadata.isSleeping() != before.isSleeping()) {
                recordables.add(
                        new RecFoxSleeping(
                                entityId,
                                metadata.isSleeping()
                        )
                );
            }

            if (metadata.isFaceplanted() != before.isFaceplanted()) {
                recordables.add(
                        new RecFoxFacePlanted(
                                entityId,
                                metadata.isFaceplanted()
                        )
                );
            }
        }

        return recordables;
    }
}