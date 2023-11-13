package mc.replay.nms.entity.metadata.other;

import mc.replay.nms.entity.metadata.EntityMetadata;
import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class PrimedTntMetadata extends EntityMetadata {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int FUSE_TIME_INDEX = OFFSET;

    public PrimedTntMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setFuseTime(int value) {
        super.metadata.setIndex(FUSE_TIME_INDEX, MetadataTypes.VarInt(value));
    }

    public int getFuseTime() {
        return super.metadata.getIndex(FUSE_TIME_INDEX, 80);
    }
}