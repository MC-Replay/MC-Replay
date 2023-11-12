package mc.replay.nms.entity.metadata.monster.skeleton;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class SkeletonMetadata extends AbstractSkeletonMetadata {

    public static final int OFFSET = AbstractSkeletonMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public SkeletonMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}