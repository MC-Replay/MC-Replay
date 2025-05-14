package mc.replay.nms.entity.metadata.monster.raider;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class AbstractIllagerMetadata extends RaiderMetadata {

    public static final int OFFSET = RaiderMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    protected AbstractIllagerMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}