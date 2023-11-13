package mc.replay.nms.entity.metadata.other;

import mc.replay.nms.entity.metadata.EntityMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class EvokerFangsMetadata extends EntityMetadata {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public EvokerFangsMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}