package mc.replay.recording.dispatcher.helpers.metadata.animal;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.animal.RecPolarBearStandingUp;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.animal.PolarBearMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.animal.PolarBearMetadata.STANDING_UP_INDEX;

public final class PolarBearMetadataReader implements MetadataReader<PolarBearMetadata> {

    @Override
    public List<Recordable> read(PolarBearMetadata before, PolarBearMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(STANDING_UP_INDEX) != null) {
            if (metadata.isStandingUp() != before.isStandingUp()) {
                recordables.add(
                        new RecPolarBearStandingUp(
                                entityId,
                                metadata.isStandingUp()
                        )
                );
            }
        }

        return recordables;
    }
}