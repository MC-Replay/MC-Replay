package mc.replay.common.recordables.actions.entity.metadata.monster;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.monster.RecEndermanCarriedBlock;
import mc.replay.nms.entity.metadata.monster.EndermanMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecEndermanCarriedBlockAction implements InternalEntityMetadataRecordableAction<RecEndermanCarriedBlock> {

    @Override
    public void writeMetadata(@NotNull RecEndermanCarriedBlock recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof EndermanMetadata endermanMetadata) {
            endermanMetadata.setCarriedBlockId(recordable.blockId());
        }
    }
}