package mc.replay.nms.entity.metadata;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class AgeableMobMetadata extends MobMetadata {

    public static final int OFFSET = MobMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int BABY_INDEX = OFFSET;

    public AgeableMobMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setBaby(boolean value) {
        if (this.isBaby() == value) return;
        super.metadata.setIndex(BABY_INDEX, Metadata.Boolean(value));
    }

    public boolean isBaby() {
        return super.metadata.getIndex(BABY_INDEX, false);
    }
}