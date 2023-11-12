package mc.replay.nms.entity.metadata.monster.raider;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class RavagerMetadata extends RaiderMetadata {

    public static final int OFFSET = RaiderMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public RavagerMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}