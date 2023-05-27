package mc.replay.recording.dispatcher.helpers.metadata.villager;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.villager.RecVillagerHeadShakeTimer;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.villager.AbstractVillagerMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.villager.AbstractVillagerMetadata.HEAD_SHAKE_TIMER_INDEX;

public final class AbstractVillagerMetadataReader implements MetadataReader<AbstractVillagerMetadata> {

    @Override
    public List<Recordable> read(AbstractVillagerMetadata before, AbstractVillagerMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(HEAD_SHAKE_TIMER_INDEX) != null) {
            if (metadata.getHeadShakeTimer() != before.getHeadShakeTimer()
                    && (metadata.getHeadShakeTimer() == 0 || metadata.getHeadShakeTimer() == 40)) {
                recordables.add(
                        new RecVillagerHeadShakeTimer(
                                entityId,
                                metadata.getHeadShakeTimer()
                        )
                );
            }
        }

        return recordables;
    }
}