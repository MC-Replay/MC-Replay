package mc.replay.nms.entity.metadata.animal;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class SkeletonHorseMetadata extends AbstractHorseMetadata {

    public static final int OFFSET = AbstractHorseMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public SkeletonHorseMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}