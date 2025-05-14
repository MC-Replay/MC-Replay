package mc.replay.nms.entity.metadata.other;

import mc.replay.nms.entity.metadata.MobMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class SlimeMetadata extends MobMetadata {

    public static final int OFFSET = MobMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int SIZE_INDEX = OFFSET;

    public SlimeMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setSize(int value) {
        super.metadata.setIndex(SIZE_INDEX, Metadata.VarInt(value));
    }

    public int getSize() {
        return super.metadata.getIndex(SIZE_INDEX, 0);
    }
}