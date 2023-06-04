package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecLlamaCarpetColor;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.animal.LlamaMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecLlamaCarpetColorAction implements InternalEntityMetadataRecordableAction<RecLlamaCarpetColor> {

    @Override
    public void writeMetadata(@NotNull RecLlamaCarpetColor recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof LlamaMetadata llamaMetadata) {
            llamaMetadata.setCarpetColor(recordable.color());
        }
    }
}