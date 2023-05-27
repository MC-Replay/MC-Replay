package mc.replay.recording.dispatcher.helpers.metadata;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.mob.RecMobBaby;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.wrapper.entity.metadata.AgeableMobMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.AgeableMobMetadata.BABY_INDEX;

public final class AgeableMobMetadataReader implements MetadataReader<AgeableMobMetadata> {

    @Override
    public List<Recordable> read(AgeableMobMetadata before, AgeableMobMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(BABY_INDEX) != null) {
            if (metadata.isBaby() != before.isBaby()) {
                recordables.add(
                        new RecMobBaby(
                                entityId,
                                metadata.isBaby()
                        )
                );
            }
        }

        return recordables;
    }
}