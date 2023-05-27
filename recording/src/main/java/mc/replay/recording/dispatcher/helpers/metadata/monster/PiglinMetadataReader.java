package mc.replay.recording.dispatcher.helpers.metadata.monster;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.mob.RecMobBaby;
import mc.replay.common.recordables.types.entity.metadata.monster.RecPiglinChargingCrossbow;
import mc.replay.common.recordables.types.entity.metadata.monster.RecPiglinDancing;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.monster.PiglinMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.monster.PiglinMetadata.*;

public final class PiglinMetadataReader implements MetadataReader<PiglinMetadata> {

    @Override
    public List<Recordable> read(PiglinMetadata before, PiglinMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
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

        if (entries.remove(CHARGING_CROSSBOW_INDEX) != null) {
            if (metadata.isChargingCrossbow() != before.isChargingCrossbow()) {
                recordables.add(
                        new RecPiglinChargingCrossbow(
                                entityId,
                                metadata.isChargingCrossbow()
                        )
                );
            }
        }

        if (entries.remove(DANCING_INDEX) != null) {
            if (metadata.isDancing() != before.isDancing()) {
                recordables.add(
                        new RecPiglinDancing(
                                entityId,
                                metadata.isDancing()
                        )
                );
            }
        }

        return recordables;
    }
}