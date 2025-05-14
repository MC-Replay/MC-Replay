package mc.replay.common.recordables.actions.entity.metadata;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.RecEntityCustomName;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public final class RecEntityCustomNameAction implements InternalEntityMetadataRecordableAction<RecEntityCustomName> {

    @Override
    public void writeMetadata(@NotNull RecEntityCustomName recordable, @NotNull EntityMetadata entityMetadata) {
        entityMetadata.setCustomName((recordable.customName() == null) ? Component.empty() : recordable.customName());
    }
}