package mc.replay.common.recordables.actions.entity.metadata.villager;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.villager.RecVillagerHeadShakeTimer;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.villager.AbstractVillagerMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecVillagerHeadShakeTimerAction implements InternalEntityMetadataRecordableAction<RecVillagerHeadShakeTimer> {

    @Override
    public void writeMetadata(@NotNull RecVillagerHeadShakeTimer recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof AbstractVillagerMetadata abstractVillagerMetadata) {
            abstractVillagerMetadata.setHeadShakeTimer(recordable.timer());
        }
    }
}