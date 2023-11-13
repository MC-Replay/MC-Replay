package mc.replay.nms.entity.metadata.arrow;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class AbstractArrowMetadata extends EntityMetadata {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 2;

    public static final int MASK_INDEX = OFFSET;
    public static final int PIERCING_LEVEL_INDEX = OFFSET + 1;

    public static final byte CRITICAL_BIT = 0x01;
    public static final byte NO_CLIP_BIT = 0x02;

    protected AbstractArrowMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setCritical(boolean value) {
        this.setMaskBit(MASK_INDEX, CRITICAL_BIT, value);
    }

    public void setNoClip(boolean value) {
        this.setMaskBit(MASK_INDEX, NO_CLIP_BIT, value);
    }

    public void setPiercingLevel(byte value) {
        super.metadata.setIndex(PIERCING_LEVEL_INDEX, Metadata.Byte(value));
    }

    public boolean isCritical() {
        return this.getMaskBit(MASK_INDEX, CRITICAL_BIT);
    }

    public boolean isNoClip() {
        return this.getMaskBit(MASK_INDEX, NO_CLIP_BIT);
    }

    public byte getPiercingLevel() {
        return super.metadata.getIndex(PIERCING_LEVEL_INDEX, (byte) 0);
    }
}