package mc.replay.recording.dispatcher.helpers.metadata.monster;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.monster.RecEndermanCarriedBlock;
import mc.replay.common.recordables.types.entity.metadata.monster.RecEndermanScreaming;
import mc.replay.common.recordables.types.entity.metadata.monster.RecEndermanStaring;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.monster.EndermanMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static mc.replay.nms.entity.metadata.monster.EndermanMetadata.*;

public final class EndermanMetadataReader implements MetadataReader<EndermanMetadata> {

    @Override
    public List<Recordable> read(EndermanMetadata before, EndermanMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(CARRIED_BLOCK_ID_INDEX) != null) {
            if (!Objects.equals(metadata.getCarriedBlockId(), before.getCarriedBlockId())) {
                recordables.add(
                        new RecEndermanCarriedBlock(
                                entityId,
                                metadata.getCarriedBlockId()
                        )
                );
            }
        }

        if (entries.remove(SCREAMING_INDEX) != null) {
            if (metadata.isScreaming() != before.isScreaming()) {
                recordables.add(
                        new RecEndermanScreaming(
                                entityId,
                                metadata.isScreaming()
                        )
                );
            }
        }

        if (entries.remove(STARING_INDEX) != null) {
            if (metadata.isStaring() != before.isStaring()) {
                recordables.add(
                        new RecEndermanStaring(
                                entityId,
                                metadata.isStaring()
                        )
                );
            }
        }

        return recordables;
    }
}