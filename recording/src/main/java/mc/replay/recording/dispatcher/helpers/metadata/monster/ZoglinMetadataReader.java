package mc.replay.recording.dispatcher.helpers.metadata.monster;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.mob.RecMobBaby;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.monster.ZoglinMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.nms.entity.metadata.monster.PiglinMetadata.BABY_INDEX;

public final class ZoglinMetadataReader implements MetadataReader<ZoglinMetadata> {

    @Override
    public List<Recordable> read(ZoglinMetadata before, ZoglinMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
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