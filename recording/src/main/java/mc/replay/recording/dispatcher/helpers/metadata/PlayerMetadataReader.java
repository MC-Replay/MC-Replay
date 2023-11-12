package mc.replay.recording.dispatcher.helpers.metadata;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.player.RecPlayerDisplayedSkinParts;
import mc.replay.common.recordables.types.entity.metadata.player.RecPlayerMainHand;
import mc.replay.common.recordables.types.entity.metadata.player.RecPlayerShoulderData;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.nms.entity.metadata.PlayerMetadata;

import java.util.*;

import static mc.replay.nms.entity.metadata.PlayerMetadata.*;

public final class PlayerMetadataReader implements MetadataReader<PlayerMetadata> {

    @Override
    public List<Recordable> read(PlayerMetadata before, PlayerMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(DISPLAYED_SKIN_PARTS_INDEX) != null) {
            recordables.add(
                    new RecPlayerDisplayedSkinParts(
                            entityId,
                            metadata.isCapeEnabled(),
                            metadata.isJacketEnabled(),
                            metadata.isLeftSleeveEnabled(),
                            metadata.isRightSleeveEnabled(),
                            metadata.isLeftLegEnabled(),
                            metadata.isRightLegEnabled(),
                            metadata.isHatEnabled()
                    )
            );
        }

        if (entries.remove(MAIN_HAND_INDEX) != null) {
            recordables.add(
                    new RecPlayerMainHand(
                            entityId,
                            metadata.isRightMainHand()
                    )
            );
        }

        if (entries.remove(RIGHT_SHOULDER_INDEX) != null) {
            recordables.add(
                    new RecPlayerShoulderData(
                            entityId,
                            true,
                            metadata.getRightShoulderEntityData()
                    )
            );
        }

        if (entries.remove(LEFT_SHOULDER_INDEX) != null) {
            recordables.add(
                    new RecPlayerShoulderData(
                            entityId,
                            false,
                            metadata.getLeftShoulderEntityData()
                    )
            );
        }

        return recordables;
    }

    @Override
    public Collection<Integer> skippedIndexes() {
        return Set.of(ADDITIONAL_HEARTS_INDEX, SCORE_INDEX);
    }
}