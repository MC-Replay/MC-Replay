package mc.replay.nms.entity.metadata.minecart;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMinecartContainerMetadata extends AbstractMinecartMetadata {

    public static final int OFFSET = AbstractMinecartMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    protected AbstractMinecartContainerMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}