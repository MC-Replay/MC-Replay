package mc.replay.nms.entity.metadata.monster.skeleton;

import mc.replay.nms.entity.metadata.monster.MonsterMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class AbstractSkeletonMetadata extends MonsterMetadata {

    public static final int OFFSET = MonsterMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    protected AbstractSkeletonMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}