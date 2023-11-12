package mc.replay.nms.entity.metadata.monster;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class VexMetadata extends MonsterMetadata {

    public static final int OFFSET = MonsterMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int ATTACKING_INDEX = OFFSET;

    public static final byte ATTACKING_BIT = 0x01;

    public VexMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setAttacking(boolean value) {
        this.setMaskBit(ATTACKING_INDEX, ATTACKING_BIT, value);
    }

    public boolean isAttacking() {
        return this.getMaskBit(ATTACKING_INDEX, ATTACKING_BIT);
    }
}