package mc.replay.nms.entity.metadata.monster;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class CaveSpiderMetadata extends SpiderMetadata {

    public static final int OFFSET = SpiderMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET;

    public CaveSpiderMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}