package mc.replay.common.recordables.actions.entity.metadata.monster;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.monster.RecEndermanScreaming;
import mc.replay.nms.entity.metadata.monster.EndermanMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecEndermanScreamingAction implements InternalEntityMetadataRecordableAction<RecEndermanScreaming> {

    @Override
    public void writeMetadata(@NotNull RecEndermanScreaming recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof EndermanMetadata endermanMetadata) {
            endermanMetadata.setScreaming(recordable.screaming());
        }
    }
}