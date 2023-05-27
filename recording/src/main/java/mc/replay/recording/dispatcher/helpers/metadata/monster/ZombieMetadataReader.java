package mc.replay.recording.dispatcher.helpers.metadata.monster;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.mob.RecMobBaby;
import mc.replay.common.recordables.types.entity.metadata.monster.RecZombieBecomingDrowned;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.monster.zombie.ZombieMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.monster.zombie.ZombieMetadata.BABY_INDEX;
import static mc.replay.wrapper.entity.metadata.monster.zombie.ZombieMetadata.BECOMING_DROWNED_INDEX;

public final class ZombieMetadataReader implements MetadataReader<ZombieMetadata> {

    @Override
    public List<Recordable> read(ZombieMetadata before, ZombieMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(BABY_INDEX) != null) {
            if (metadata.isBaby() != before.isBaby()) {
                recordables.add(
                        new RecMobBaby(
                                entityId,
                                metadata.isBaby()
                        )
                );
            }
        }

        if (entries.remove(BECOMING_DROWNED_INDEX) != null) {
            if (metadata.isBecomingDrowned() != before.isBecomingDrowned()) {
                recordables.add(
                        new RecZombieBecomingDrowned(
                                entityId,
                                metadata.isBecomingDrowned()
                        )
                );
            }
        }

        return recordables;
    }
}