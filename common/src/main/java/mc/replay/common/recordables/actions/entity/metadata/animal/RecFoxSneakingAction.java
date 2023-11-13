package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecFoxSneaking;
import mc.replay.nms.entity.metadata.animal.FoxMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecFoxSneakingAction implements InternalEntityMetadataRecordableAction<RecFoxSneaking> {

    @Override
    public void writeMetadata(@NotNull RecFoxSneaking recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof FoxMetadata beeMetadata) {
            beeMetadata.setFoxSneaking(recordable.sneaking());
        }
    }
}