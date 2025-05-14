package mc.replay.nms.entity.metadata.monster.raider;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class VindicatorMetadata extends AbstractIllagerMetadata {

    public static final int OFFSET = AbstractIllagerMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public VindicatorMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}