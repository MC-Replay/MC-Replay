package mc.replay.nms.entity.metadata.flying;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class PhantomMetadata extends FlyingMetadata {

    public static final int OFFSET = FlyingMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int SIZE_INDEX = OFFSET;

    public PhantomMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setSize(int value) {
        super.metadata.setIndex(SIZE_INDEX, MetadataTypes.VarInt(value));
    }

    public int getSize() {
        return super.metadata.getIndex(SIZE_INDEX, 0);
    }
}