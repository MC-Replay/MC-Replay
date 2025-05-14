package mc.replay.common.recordables.actions.entity.metadata.minecart;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.minecart.RecFurnaceMinecartFuel;
import mc.replay.nms.entity.metadata.minecart.FurnaceMinecartMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecFurnaceMinecartFuelAction implements InternalEntityMetadataRecordableAction<RecFurnaceMinecartFuel> {

    @Override
    public void writeMetadata(@NotNull RecFurnaceMinecartFuel recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof FurnaceMinecartMetadata furnaceMinecartMetadata) {
            furnaceMinecartMetadata.setHasFuel(recordable.fuel());
        }
    }
}