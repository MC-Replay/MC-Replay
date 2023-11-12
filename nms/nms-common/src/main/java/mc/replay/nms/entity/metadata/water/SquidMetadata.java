package mc.replay.nms.entity.metadata.water;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class SquidMetadata extends WaterAnimalMetadata {

    public static final int OFFSET = WaterAnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public SquidMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}