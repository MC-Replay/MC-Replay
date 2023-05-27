package mc.replay.recording.dispatcher.helpers.metadata.monster;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.monster.RecZombieVillagerConverting;
import mc.replay.common.recordables.types.entity.metadata.villager.RecVillagerData;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.monster.zombie.ZombieVillagerMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static mc.replay.wrapper.entity.metadata.monster.zombie.ZombieVillagerMetadata.CONVERTING_INDEX;
import static mc.replay.wrapper.entity.metadata.monster.zombie.ZombieVillagerMetadata.VILLAGER_DATA_INDEX;

public final class ZombieVillagerMetadataReader implements MetadataReader<ZombieVillagerMetadata> {

    @Override
    public List<Recordable> read(ZombieVillagerMetadata before, ZombieVillagerMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(CONVERTING_INDEX) != null) {
            if (metadata.isConverting() != before.isConverting()) {
                recordables.add(
                        new RecZombieVillagerConverting(
                                entityId,
                                metadata.isConverting()
                        )
                );
            }
        }

        Metadata.Entry<?> entry;
        if ((entry = entries.remove(VILLAGER_DATA_INDEX)) != null) {
            int[] data = (int[]) entry.value();

            if (!Objects.equals(metadata.getVillagerData(), before.getVillagerData())) {
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