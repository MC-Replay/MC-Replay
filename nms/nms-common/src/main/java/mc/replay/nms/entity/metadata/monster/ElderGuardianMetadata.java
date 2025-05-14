package mc.replay.nms.entity.metadata.monster;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class ElderGuardianMetadata extends GuardianMetadata {

    public static final int OFFSET = GuardianMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public ElderGuardianMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}