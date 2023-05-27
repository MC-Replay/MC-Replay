package mc.replay.common.recordables.actions.entity.metadata.living;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.living.RecLivingEntityHandState;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.LivingEntityMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecLivingEntityHandStateAction implements InternalEntityMetadataRecordableAction<RecLivingEntityHandState> {

    @Override
    public void writeMetadata(@NotNull RecLivingEntityHandState recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof LivingEntityMetadata livingEntityMetadata) {
            livingEntityMetadata.setHandActive(recordable.handActive());
            livingEntityMetadata.setActiveHand(recordable.activeHand());
            livingEntityMetadata.setInRiptideSpinAttack(recordable.inSpinAttack());
        }
    }
}