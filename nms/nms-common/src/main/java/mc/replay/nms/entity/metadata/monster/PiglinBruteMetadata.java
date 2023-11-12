package mc.replay.nms.entity.metadata.monster;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class PiglinBruteMetadata extends BasePiglinMetadata {

    public static final int OFFSET = BasePiglinMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public PiglinBruteMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}