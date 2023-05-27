package mc.replay.common.recordables.actions.entity.metadata;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.RecEntityCombust;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecEntityCombustAction implements InternalEntityMetadataRecordableAction<RecEntityCombust> {

    @Override
    public void writeMetadata(@NotNull RecEntityCombust recordable, @NotNull EntityMetadata entityMetadata) {
        entityMetadata.setOnFire(recordable.combust());
    }
}