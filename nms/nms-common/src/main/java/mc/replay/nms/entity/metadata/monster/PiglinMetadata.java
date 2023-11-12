package mc.replay.nms.entity.metadata.monster;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class PiglinMetadata extends BasePiglinMetadata {

    public static final int OFFSET = BasePiglinMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 3;

    public static final int BABY_INDEX = OFFSET;
    public static final int CHARGING_CROSSBOW_INDEX = OFFSET + 1;
    public static final int DANCING_INDEX = OFFSET + 2;

    public PiglinMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setBaby(boolean value) {
        if (this.isBaby() == value) return;
        super.metadata.setIndex(BABY_INDEX, Metadata.Boolean(value));
    }

    public void setChargingCrossbow(boolean value) {
        super.metadata.setIndex(CHARGING_CROSSBOW_INDEX, Metadata.Boolean(value));
    }

    public void setDancing(boolean value) {
        super.metadata.setIndex(DANCING_INDEX, Metadata.Boolean(value));
    }

    public boolean isBaby() {
        return super.metadata.getIndex(BABY_INDEX, false);
    }

    public boolean isChargingCrossbow() {
        return super.metadata.getIndex(CHARGING_CROSSBOW_INDEX, false);
    }

    public boolean isDancing() {
        return super.metadata.getIndex(DANCING_INDEX, false);
    }
}