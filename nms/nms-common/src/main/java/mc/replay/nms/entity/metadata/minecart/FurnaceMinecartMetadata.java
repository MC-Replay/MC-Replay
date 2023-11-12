package mc.replay.nms.entity.metadata.minecart;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class FurnaceMinecartMetadata extends AbstractMinecartMetadata {

    public static final int OFFSET = AbstractMinecartMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int FUEL_INDEX = OFFSET;

    public FurnaceMinecartMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setHasFuel(boolean value) {
        super.metadata.setIndex(FUEL_INDEX, Metadata.Boolean(value));
    }

    public boolean hasFuel() {
        return metadata.getIndex(FUEL_INDEX, false);
    }

    @Override
    public int getObjectData() {
        return 2;
    }
}