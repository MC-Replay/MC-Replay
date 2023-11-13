package mc.replay.recording.dispatcher.helpers.metadata.monster;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.monster.RecCreeperCharged;
import mc.replay.common.recordables.types.entity.metadata.monster.RecCreeperState;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.monster.CreeperMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.nms.entity.metadata.monster.CreeperMetadata.CHARGED_INDEX;
import static mc.replay.nms.entity.metadata.monster.CreeperMetadata.STATE_INDEX;

public final class CreeperMetadataReader implements MetadataReader<CreeperMetadata> {

    @Override
    public List<Recordable> read(CreeperMetadata before, CreeperMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(STATE_INDEX) != null) {
            if (metadata.getState() != before.getState()) {
                recordables.add(
                        new RecCreeperState(
                                entityId,
                                metadata.getState().ordinal()
                        )
                );
            }
        }

        if (entries.remove(CHARGED_INDEX) != null) {
            if (metadata.isCharged() != before.isCharged()) {
                recordables.add(
                        new RecCreeperCharged(
                                entityId,
                                metadata.isCharged()
                        )
                );
            }
        }

        return recordables;
    }
}