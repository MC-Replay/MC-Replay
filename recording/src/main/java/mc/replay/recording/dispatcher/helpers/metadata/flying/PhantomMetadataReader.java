package mc.replay.recording.dispatcher.helpers.metadata.flying;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.flying.RecPhantomSize;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.flying.PhantomMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.nms.entity.metadata.flying.PhantomMetadata.SIZE_INDEX;

public final class PhantomMetadataReader implements MetadataReader<PhantomMetadata> {

    @Override
    public List<Recordable> read(PhantomMetadata before, PhantomMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(SIZE_INDEX) != null) {
            if (metadata.getSize() != before.getSize()) {
                recordables.add(
                        new RecPhantomSize(
                                entityId,
                                metadata.getSize()
                        )
                );
            }
        }

        return recordables;
    }
}