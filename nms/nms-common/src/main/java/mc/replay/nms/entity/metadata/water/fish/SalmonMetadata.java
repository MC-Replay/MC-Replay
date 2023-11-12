package mc.replay.nms.entity.metadata.water.fish;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class SalmonMetadata extends AbstractFishMetadata {

    public static final int OFFSET = AbstractFishMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public SalmonMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}