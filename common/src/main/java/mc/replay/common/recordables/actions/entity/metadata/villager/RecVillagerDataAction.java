package mc.replay.common.recordables.actions.entity.metadata.villager;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.villager.RecVillagerData;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.villager.VillagerMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecVillagerDataAction implements InternalEntityMetadataRecordableAction<RecVillagerData> {

    @Override
    public void writeMetadata(@NotNull RecVillagerData recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof VillagerMetadata villagerMetadata) {
            villagerMetadata.getMetadata().setIndex(VillagerMetadata.VILLAGER_DATA_INDEX, Metadata.VillagerData(
                    recordable.data()[0],
                    recordable.data()[1],
                    recordable.data()[2]
            ));
        }
    }
}