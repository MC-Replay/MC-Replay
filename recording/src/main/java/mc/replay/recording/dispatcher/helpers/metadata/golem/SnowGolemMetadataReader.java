package mc.replay.recording.dispatcher.helpers.metadata.golem;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.golem.RecSnowGolemPumpkinHat;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.golem.SnowGolemMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.golem.SnowGolemMetadata.PUMPKIN_HAT_INDEX;

public final class SnowGolemMetadataReader implements MetadataReader<SnowGolemMetadata> {

    @Override
    public List<Recordable> read(SnowGolemMetadata before, SnowGolemMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(PUMPKIN_HAT_INDEX) != null) {
            if (metadata.hasPumpkinHat() != before.hasPumpkinHat()) {
                recordables.add(
                        new RecSnowGolemPumpkinHat(
                                entityId,
                                metadata.hasPumpkinHat()
                        )
                );
            }
        }

        return recordables;
    }
}