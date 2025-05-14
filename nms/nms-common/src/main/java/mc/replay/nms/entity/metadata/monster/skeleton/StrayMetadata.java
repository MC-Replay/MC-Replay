package mc.replay.nms.entity.metadata.monster.skeleton;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class StrayMetadata extends AbstractSkeletonMetadata {

    public static final int OFFSET = AbstractSkeletonMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public StrayMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}