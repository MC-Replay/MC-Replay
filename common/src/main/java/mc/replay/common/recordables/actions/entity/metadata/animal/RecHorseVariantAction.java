package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecHorseVariant;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.animal.HorseMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecHorseVariantAction implements InternalEntityMetadataRecordableAction<RecHorseVariant> {

    @Override
    public void writeMetadata(@NotNull RecHorseVariant recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof HorseMetadata horseMetadata) {
            horseMetadata.setVariant(HorseMetadata.getVariantFromId(recordable.variant()));
        }
    }
}