package mc.replay.recording.dispatcher.helpers.metadata.monster;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.RecEntityCombust;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.monster.BlazeMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.monster.BlazeMetadata.MASK_INDEX;

public final class BlazeMetadataReader implements MetadataReader<BlazeMetadata> {

    @Override
    public List<Recordable> read(BlazeMetadata before, BlazeMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(MASK_INDEX) != null) {
            if (before.isOnFire() != metadata.isOnFire()) {
                recordables.add(
                        new RecEntityCombust(
                                entityId,
                                metadata.isOnFire()
                        )
                );
            }
        }

        return recordables;
    }
}