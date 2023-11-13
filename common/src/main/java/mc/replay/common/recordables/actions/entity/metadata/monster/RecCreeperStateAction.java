package mc.replay.common.recordables.actions.entity.metadata.monster;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.monster.RecCreeperState;
import mc.replay.nms.entity.metadata.monster.CreeperMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecCreeperStateAction implements InternalEntityMetadataRecordableAction<RecCreeperState> {

    @Override
    public void writeMetadata(@NotNull RecCreeperState recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof CreeperMetadata creeperMetadata) {
            creeperMetadata.setState(CreeperMetadata.State.values()[recordable.state()]);
        }
    }
}