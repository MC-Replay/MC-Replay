package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecPandaOnBack;
import mc.replay.nms.entity.metadata.animal.PandaMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecPandaOnBackAction implements InternalEntityMetadataRecordableAction<RecPandaOnBack> {

    @Override
    public void writeMetadata(@NotNull RecPandaOnBack recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof PandaMetadata pandaMetadata) {
            pandaMetadata.setOnBack(recordable.onBack());
        }
    }
}