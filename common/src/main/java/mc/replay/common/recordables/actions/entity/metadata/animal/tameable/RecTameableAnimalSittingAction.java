package mc.replay.common.recordables.actions.entity.metadata.animal.tameable;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.tameable.RecTameableAnimalSitting;
import mc.replay.nms.entity.metadata.animal.tameable.TameableAnimalMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecTameableAnimalSittingAction implements InternalEntityMetadataRecordableAction<RecTameableAnimalSitting> {

    @Override
    public void writeMetadata(@NotNull RecTameableAnimalSitting recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof TameableAnimalMetadata tameableAnimalMetadata) {
            tameableAnimalMetadata.setSitting(recordable.sitting());
        }
    }
}