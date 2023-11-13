package mc.replay.nms.entity.metadata.monster.zombie;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class DrownedMetadata extends ZombieMetadata {

    public static final int OFFSET = ZombieMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public DrownedMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}