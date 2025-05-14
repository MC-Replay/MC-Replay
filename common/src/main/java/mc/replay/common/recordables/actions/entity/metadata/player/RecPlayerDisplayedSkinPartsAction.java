package mc.replay.common.recordables.actions.entity.metadata.player;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.player.RecPlayerDisplayedSkinParts;
import mc.replay.nms.entity.metadata.PlayerMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecPlayerDisplayedSkinPartsAction implements InternalEntityMetadataRecordableAction<RecPlayerDisplayedSkinParts> {

    @Override
    public void writeMetadata(@NotNull RecPlayerDisplayedSkinParts recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof PlayerMetadata playerMetadata) {
            playerMetadata.setCapeEnabled(recordable.cape());
            playerMetadata.setJacketEnabled(recordable.jacket());
            playerMetadata.setLeftSleeveEnabled(recordable.leftSleeve());
            playerMetadata.setRightSleeveEnabled(recordable.rightSleeve());
            playerMetadata.setLeftLegEnabled(recordable.leftPants());
            playerMetadata.setRightLegEnabled(recordable.rightPants());
            playerMetadata.setHatEnabled(recordable.hat());
        }
    }
}