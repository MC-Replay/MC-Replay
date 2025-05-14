package mc.replay.common.recordables.actions.entity.metadata.monster;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.monster.RecZombieBecomingDrowned;
import mc.replay.nms.entity.metadata.monster.zombie.ZombieMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecZombieBecomingDrownedAction implements InternalEntityMetadataRecordableAction<RecZombieBecomingDrowned> {

    @Override
    public void writeMetadata(@NotNull RecZombieBecomingDrowned recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof ZombieMetadata zombieMetadata) {
            zombieMetadata.setBecomingDrowned(recordable.becomingDrowned());
        }
    }
}