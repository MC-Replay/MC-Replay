package mc.replay.recording.dispatcher.helpers.metadata.water;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.water.RecPufferfishState;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.water.fish.PufferfishMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.nms.entity.metadata.water.fish.PufferfishMetadata.STATE_INDEX;

public final class PufferfishMetadataReader implements MetadataReader<PufferfishMetadata> {

    @Override
    public List<Recordable> read(PufferfishMetadata before, PufferfishMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(STATE_INDEX) != null) {
            if (metadata.getState() != before.getState()) {
                recordables.add(
                        new RecPufferfishState(
                                entityId,
                                metadata.getState().ordinal()
                        )
                );
            }
        }

        return recordables;
    }
}