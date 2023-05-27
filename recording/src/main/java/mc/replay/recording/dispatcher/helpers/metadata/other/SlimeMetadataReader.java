package mc.replay.recording.dispatcher.helpers.metadata.other;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.other.RecSlimeSize;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.other.SlimeMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.other.SlimeMetadata.SIZE_INDEX;

public final class SlimeMetadataReader implements MetadataReader<SlimeMetadata> {

    @Override
    public List<Recordable> read(SlimeMetadata before, SlimeMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(SIZE_INDEX) != null) {
            if (metadata.getSize() != before.getSize()) {
                recordables.add(
                        new RecSlimeSize(
                                entityId,
                                metadata.getSize()
                        )
                );
            }
        }

        return recordables;
    }
}