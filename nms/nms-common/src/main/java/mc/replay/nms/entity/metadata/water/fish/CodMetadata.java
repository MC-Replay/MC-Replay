package mc.replay.nms.entity.metadata.water.fish;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class CodMetadata extends AbstractFishMetadata {

    public static final int OFFSET = AbstractFishMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public CodMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}