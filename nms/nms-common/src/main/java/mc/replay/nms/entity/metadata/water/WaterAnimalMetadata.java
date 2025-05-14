package mc.replay.nms.entity.metadata.water;

import mc.replay.nms.entity.metadata.MobMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class WaterAnimalMetadata extends MobMetadata {

    public static final int OFFSET = MobMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    protected WaterAnimalMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}