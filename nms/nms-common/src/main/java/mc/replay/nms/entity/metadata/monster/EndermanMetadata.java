package mc.replay.nms.entity.metadata.monster;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EndermanMetadata extends MonsterMetadata {

    public static final int OFFSET = MonsterMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 3;

    public static final int CARRIED_BLOCK_ID_INDEX = OFFSET;
    public static final int SCREAMING_INDEX = OFFSET + 1;
    public static final int STARING_INDEX = OFFSET + 2;

    public EndermanMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setCarriedBlockId(@Nullable Integer value) {
        super.metadata.setIndex(CARRIED_BLOCK_ID_INDEX, MetadataTypes.OptBlockState(value));
    }

    public void setScreaming(boolean value) {
        super.metadata.setIndex(SCREAMING_INDEX, MetadataTypes.Boolean(value));
    }

    public void setStaring(boolean value) {
        super.metadata.setIndex(STARING_INDEX, MetadataTypes.Boolean(value));
    }

    public @Nullable Integer getCarriedBlockId() {
        return super.metadata.getIndex(CARRIED_BLOCK_ID_INDEX, null);
    }

    public boolean isScreaming() {
        return super.metadata.getIndex(SCREAMING_INDEX, false);
    }

    public boolean isStaring() {
        return super.metadata.getIndex(STARING_INDEX, false);
    }
}