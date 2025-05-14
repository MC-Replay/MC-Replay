package mc.replay.nms.entity.metadata.water.fish;

import mc.replay.nms.entity.metadata.water.WaterAnimalMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class AbstractFishMetadata extends WaterAnimalMetadata {

    public static final int OFFSET = WaterAnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int FROM_BUCKET_INDEX = OFFSET;

    protected AbstractFishMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setFromBucket(boolean fromBucket) {
        super.metadata.setIndex(FROM_BUCKET_INDEX, Metadata.Boolean(fromBucket));
    }

    public boolean isFromBucket() {
        return super.metadata.getIndex(FROM_BUCKET_INDEX, false);
    }
}