package mc.replay.nms.entity.metadata;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class MobMetadata extends LivingEntityMetadata {

    public static final int OFFSET = LivingEntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int MASK_INDEX = OFFSET;

    public final static byte NO_AI_BIT = 0x01;
    public final static byte IS_LEFT_HANDED_BIT = 0x02;
    public final static byte IS_AGGRESSIVE_BIT = 0x04;

    public MobMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setAi(boolean value) {
        this.setMaskBit(MASK_INDEX, NO_AI_BIT, !value);
    }

    public void setLeftHanded(boolean value) {
        this.setMaskBit(MASK_INDEX, IS_LEFT_HANDED_BIT, value);
    }

    public void setAggressive(boolean value) {
        this.setMaskBit(MASK_INDEX, IS_AGGRESSIVE_BIT, value);
    }

    public boolean hasAi() {
        return !this.getMaskBit(MASK_INDEX, NO_AI_BIT);
    }

    public boolean isLeftHanded() {
        return this.getMaskBit(MASK_INDEX, IS_LEFT_HANDED_BIT);
    }

    public boolean isAggressive() {
        return this.getMaskBit(MASK_INDEX, IS_AGGRESSIVE_BIT);
    }
}