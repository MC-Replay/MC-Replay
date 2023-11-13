package mc.replay.common.recordables.actions.entity.metadata.monster;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.monster.RecCreeperCharged;
import mc.replay.nms.entity.metadata.monster.CreeperMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecCreeperChargedAction implements InternalEntityMetadataRecordableAction<RecCreeperCharged> {

    @Override
    public void writeMetadata(@NotNull RecCreeperCharged recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof CreeperMetadata creeperMetadata) {
            creeperMetadata.setCharged(recordable.charged());
        }
    }
}