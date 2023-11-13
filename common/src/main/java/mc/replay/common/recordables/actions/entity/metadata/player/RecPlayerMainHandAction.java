package mc.replay.common.recordables.actions.entity.metadata.player;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.player.RecPlayerMainHand;
import mc.replay.nms.entity.metadata.PlayerMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecPlayerMainHandAction implements InternalEntityMetadataRecordableAction<RecPlayerMainHand> {

    @Override
    public void writeMetadata(@NotNull RecPlayerMainHand recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof PlayerMetadata playerMetadata) {
            playerMetadata.setRightMainHand(recordable.right());
        }
    }
}