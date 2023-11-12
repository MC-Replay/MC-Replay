package mc.replay.nms.entity.metadata.minecart;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class HopperMinecartMetadata extends AbstractMinecartContainerMetadata {

    public static final int OFFSET = AbstractMinecartContainerMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public HopperMinecartMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    @Override
    public int getObjectData() {
        return 5;
    }
}