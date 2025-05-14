package mc.replay.nms.entity.metadata.monster;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class BlazeMetadata extends MonsterMetadata {

    public static final int OFFSET = MonsterMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int MASK_INDEX = OFFSET;

    public static final byte ON_FIRE_BIT = 0x01;

    public BlazeMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setOnFire(boolean value) {
        this.setMaskBit(MASK_INDEX, ON_FIRE_BIT, value);
    }

    public boolean isOnFire() {
        return this.getMaskBit(MASK_INDEX, ON_FIRE_BIT);
    }
}