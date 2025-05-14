package mc.replay.nms.entity.metadata.animal;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class SheepMetadata extends AnimalMetadata {

    public static final int OFFSET = AnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int MASK_INDEX = OFFSET;

    public static final byte COLOR_BITS = 0x0F;
    public static final byte SHEARED_BIT = 0x10;

    public SheepMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setColor(byte color) {
        byte before = this.getMask(OFFSET);
        byte mask = before;
        mask &= ~COLOR_BITS;
        mask |= (color & COLOR_BITS);
        if (mask != before) {
            this.setMask(OFFSET, mask);
        }
    }

    public void setSheared(boolean value) {
        this.setMaskBit(MASK_INDEX, SHEARED_BIT, value);
    }

    public int getColor() {
        return this.getMask(MASK_INDEX) & COLOR_BITS;
    }

    public boolean isSheared() {
        return this.getMaskBit(MASK_INDEX, SHEARED_BIT);
    }
}