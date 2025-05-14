package mc.replay.common.recordables.actions.entity.metadata.player;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.player.RecPlayerShoulderData;
import mc.replay.nms.entity.metadata.PlayerMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecPlayerShoulderDataAction implements InternalEntityMetadataRecordableAction<RecPlayerShoulderData> {

    @Override
    public void writeMetadata(@NotNull RecPlayerShoulderData recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof PlayerMetadata playerMetadata) {
            if (recordable.right()) {
                playerMetadata.setRightShouldEntityData(recordable.shoulderData());
            } else {
                playerMetadata.setLeftShouldEntityData(recordable.shoulderData());
            }
        }
    }
}