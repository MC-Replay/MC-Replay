package mc.replay.nms.entity.metadata.other;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class MagmaCubeMetadata extends SlimeMetadata {

    public static final int OFFSET = SlimeMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public MagmaCubeMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}