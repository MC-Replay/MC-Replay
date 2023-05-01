package mc.replay.recording.dispatcher.helpers.metadata;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.RecEntityArrowCount;
import mc.replay.common.recordables.types.entity.metadata.RecEntityBeeStingerCount;
import mc.replay.common.recordables.types.entity.metadata.RecEntityHandState;
import mc.replay.common.recordables.types.entity.metadata.RecEntityHealth;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.wrapper.entity.metadata.LivingEntityMetadata;

import java.util.*;

import static mc.replay.wrapper.entity.metadata.LivingEntityMetadata.*;

public final class LivingEntityMetadataReader implements MetadataReader<LivingEntityMetadata> {

    @Override
    public List<Recordable> read(LivingEntityMetadata before, LivingEntityMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(HAND_STATES_MASK_INDEX) != null) {
            recordables.add(
                    new RecEntityHandState(
                            entityId,
                            metadata.isHandActive(),
                            metadata.getActiveHand(),
                            metadata.isInRiptideSpinAttack()
                    )
            );
        }

        if (entries.remove(HEALTH_INDEX) != null) {
            recordables.add(
                    new RecEntityHealth(
                            entityId,
                            metadata.getHealth()
                    )
            );
        }

        if (entries.remove(ARROW_COUNT_INDEX) != null) {
            recordables.add(
                    new RecEntityArrowCount(
                            entityId,
                            metadata.getArrowCount()
                    )
            );
        }

        if (entries.remove(BEE_STINGER_COUNT) != null) {
            recordables.add(
                    new RecEntityBeeStingerCount(
                            entityId,
                            metadata.getBeeStingerCount()
                    )
            );
        }

        return recordables;
    }

    @Override
    public Collection<Integer> skippedIndexes() {
        return Set.of(POTION_EFFECT_COLOR_INDEX, POTION_EFFECT_AMBIENT_INDEX, BED_POSITION_INDEX);
    }
}