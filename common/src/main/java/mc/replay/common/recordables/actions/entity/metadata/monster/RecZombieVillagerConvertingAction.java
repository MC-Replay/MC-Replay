package mc.replay.common.recordables.actions.entity.metadata.monster;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.monster.RecZombieVillagerConverting;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.monster.zombie.ZombieVillagerMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecZombieVillagerConvertingAction implements InternalEntityMetadataRecordableAction<RecZombieVillagerConverting> {

    @Override
    public void writeMetadata(@NotNull RecZombieVillagerConverting recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof ZombieVillagerMetadata zombieVillagerMetadata) {
            zombieVillagerMetadata.setConverting(recordable.converting());
        }
    }
}