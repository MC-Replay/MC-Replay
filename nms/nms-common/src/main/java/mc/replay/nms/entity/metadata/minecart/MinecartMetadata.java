package mc.replay.nms.entity.metadata.minecart;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class MinecartMetadata extends AbstractMinecartMetadata {

    public static final int OFFSET = AbstractMinecartMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public MinecartMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    @Override
    public int getObjectData() {
        return 0;
    }
}