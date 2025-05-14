package mc.replay.common.recordables.actions.entity.metadata;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.RecEntityCombust;
import org.jetbrains.annotations.NotNull;

public final class RecEntityCombustAction implements InternalEntityMetadataRecordableAction<RecEntityCombust> {

    @Override
    public void writeMetadata(@NotNull RecEntityCombust recordable, @NotNull EntityMetadata entityMetadata) {
        entityMetadata.setOnFire(recordable.combust());
    }
}