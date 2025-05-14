package mc.replay.nms.entity.metadata.monster;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class GiantMetadata extends MonsterMetadata {

    public static final int OFFSET = MonsterMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public GiantMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}