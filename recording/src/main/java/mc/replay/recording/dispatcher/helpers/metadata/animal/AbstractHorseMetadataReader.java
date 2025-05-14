package mc.replay.recording.dispatcher.helpers.metadata.animal;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.animal.RecAbstractHorseEating;
import mc.replay.common.recordables.types.entity.metadata.animal.RecAbstractHorseMouthOpen;
import mc.replay.common.recordables.types.entity.metadata.animal.RecAbstractHorseRearing;
import mc.replay.common.recordables.types.entity.metadata.animal.RecAbstractHorseSaddled;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.animal.AbstractHorseMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.nms.entity.metadata.animal.AbstractHorseMetadata.MASK_INDEX;

public final class AbstractHorseMetadataReader implements MetadataReader<AbstractHorseMetadata> {

    @Override
    public List<Recordable> read(AbstractHorseMetadata before, AbstractHorseMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(MASK_INDEX) != null) {
            if (metadata.isSaddled() != before.isSaddled()) {
                recordables.add(
                        new RecAbstractHorseSaddled(
                                entityId,
                                metadata.isSaddled()
                        )
                );
            }

            if (metadata.isEating() != before.isEating()) {
                recordables.add(
                        new RecAbstractHorseEating(
                                entityId,
                                metadata.isEating()
                        )
                );
            }

            if (metadata.isRearing() != before.isRearing()) {
                recordables.add(
                        new RecAbstractHorseRearing(
                                entityId,
                                metadata.isRearing()
                        )
                );
            }

            if (metadata.isMouthOpen() != before.isMouthOpen()) {
                recordables.add(
                        new RecAbstractHorseMouthOpen(
                                entityId,
                                metadata.isMouthOpen()
                        )
                );
            }
        }

        return recordables;
    }
}