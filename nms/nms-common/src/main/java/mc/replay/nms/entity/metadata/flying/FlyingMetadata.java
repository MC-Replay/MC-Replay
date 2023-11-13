package mc.replay.nms.entity.metadata.flying;

import mc.replay.nms.entity.metadata.MobMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class FlyingMetadata extends MobMetadata {

    public static final int OFFSET = MobMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    protected FlyingMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}