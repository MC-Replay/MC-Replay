package mc.replay.recording.dispatcher.helpers.metadata.animal;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.animal.RecSheepColor;
import mc.replay.common.recordables.types.entity.metadata.animal.RecSheepSheared;
import mc.replay.nms.entity.metadata.animal.SheepMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.nms.entity.metadata.animal.SheepMetadata.MASK_INDEX;

public final class SheepMetadataReader implements MetadataReader<SheepMetadata> {

    @Override
    public List<Recordable> read(SheepMetadata before, SheepMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(MASK_INDEX) != null) {
            if (metadata.getColor() != before.getColor()) {
                recordables.add(
                        new RecSheepColor(
                                entityId,
                                (byte) metadata.getColor()
                        )
                );
            }

            if (metadata.isSheared() != before.isSheared()) {
                recordables.add(
                        new RecSheepSheared(
                                entityId,
                                metadata.isSheared()
                        )
                );
            }
        }

        return recordables;
    }
}