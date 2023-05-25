package mc.replay.recording.dispatcher.helpers.metadata;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.mob.RecMobLeftHanded;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.wrapper.entity.metadata.MobMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.MobMetadata.MASK_INDEX;

public final class MobMetadataReader implements MetadataReader<MobMetadata> {

    @Override
    public List<Recordable> read(MobMetadata before, MobMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(MASK_INDEX) != null) {
            if (before.isLeftHanded() != metadata.isLeftHanded()) {
                recordables.add(
                        new RecMobLeftHanded(
                                entityId,
                                metadata.isLeftHanded()
                        )
                );
            }
        }

        return recordables;
    }
}