package mc.replay.common.recordables.actions.entity.metadata.water;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.water.RecPufferfishState;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.water.fish.PufferfishMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecPufferfishStateAction implements InternalEntityMetadataRecordableAction<RecPufferfishState> {

    @Override
    public void writeMetadata(@NotNull RecPufferfishState recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof PufferfishMetadata pufferfishMetadata) {
            pufferfishMetadata.setState(PufferfishMetadata.State.values()[recordable.state()]);
        }
    }
}