package mc.replay.common.recordables.actions.entity.metadata.monster;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.monster.RecZombieVillagerConverting;
import mc.replay.nms.entity.metadata.monster.zombie.ZombieVillagerMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecZombieVillagerConvertingAction implements InternalEntityMetadataRecordableAction<RecZombieVillagerConverting> {

    @Override
    public void writeMetadata(@NotNull RecZombieVillagerConverting recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof ZombieVillagerMetadata zombieVillagerMetadata) {
            zombieVillagerMetadata.setConverting(recordable.converting());
        }
    }
}