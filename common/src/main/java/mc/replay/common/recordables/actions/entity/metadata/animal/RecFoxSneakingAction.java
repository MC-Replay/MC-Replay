package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecFoxSneaking;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.animal.FoxMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecFoxSneakingAction implements InternalEntityMetadataRecordableAction<RecFoxSneaking> {

    @Override
    public void writeMetadata(@NotNull RecFoxSneaking recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof FoxMetadata beeMetadata) {
            beeMetadata.setFoxSneaking(recordable.sneaking());
        }
    }
}