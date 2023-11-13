package mc.replay.nms.entity.metadata.monster;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class ZoglinMetadata extends MonsterMetadata {

    public static final int OFFSET = MonsterMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int BABY_INDEX = OFFSET;

    public ZoglinMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setBaby(boolean value) {
        if (this.isBaby() == value) return;
        super.metadata.setIndex(BABY_INDEX, MetadataTypes.Boolean(value));
    }

    public boolean isBaby() {
        return super.metadata.getIndex(BABY_INDEX, false);
    }
}