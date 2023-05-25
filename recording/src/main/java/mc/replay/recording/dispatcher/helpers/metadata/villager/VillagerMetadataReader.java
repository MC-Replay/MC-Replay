package mc.replay.recording.dispatcher.helpers.metadata.villager;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.villager.RecVillagerData;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.villager.VillagerMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.villager.VillagerMetadata.VILLAGER_DATA_INDEX;

public final class VillagerMetadataReader implements MetadataReader<VillagerMetadata> {

    @Override
    public List<Recordable> read(VillagerMetadata before, VillagerMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        Metadata.Entry<?> entry;
        if ((entry = entries.remove(VILLAGER_DATA_INDEX)) != null) {
            int[] data = (int[]) entry.value();

            if (!metadata.getVillagerData().equals(metadata.getVillagerData())) {
                recordables.add(
                        new RecVillagerData(
                                entityId,
                                data
                        )
                );
            }
        }

        return recordables;
    }
}