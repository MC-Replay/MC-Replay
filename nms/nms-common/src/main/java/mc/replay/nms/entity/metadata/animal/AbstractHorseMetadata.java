package mc.replay.nms.entity.metadata.animal;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class AbstractHorseMetadata extends AnimalMetadata {

    public static final int OFFSET = AnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 2;

    public static final int MASK_INDEX = OFFSET;
    public static final int OWNER_INDEX = OFFSET + 1;

    public static final byte TAMED_BIT = 0x02;
    public static final byte SADDLED_BIT = 0x04;
    public static final byte HAS_BRED_BIT = 0x08;
    public static final byte EATING_BIT = 0x10;
    public static final byte REARING_BIT = 0x20;
    public static final byte MOUTH_OPEN_BIT = 0x40;

    protected AbstractHorseMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setTamed(boolean value) {
        this.setMaskBit(MASK_INDEX, TAMED_BIT, value);
    }

    public void setSaddled(boolean value) {
        this.setMaskBit(MASK_INDEX, SADDLED_BIT, value);
    }

    public void setHasBred(boolean value) {
        this.setMaskBit(MASK_INDEX, HAS_BRED_BIT, value);
    }

    public void setEating(boolean value) {
        this.setMaskBit(MASK_INDEX, EATING_BIT, value);
    }

    public void setRearing(boolean value) {
        this.setMaskBit(MASK_INDEX, REARING_BIT, value);
    }

    public void setMouthOpen(boolean value) {
        this.setMaskBit(MASK_INDEX, MOUTH_OPEN_BIT, value);
    }

    public void setOwner(@Nullable UUID value) {
        super.metadata.setIndex(OWNER_INDEX, MetadataTypes.OptUUID(value));
    }

    public boolean isTamed() {
        return this.getMaskBit(MASK_INDEX, TAMED_BIT);
    }

    public boolean isSaddled() {
        return this.getMaskBit(MASK_INDEX, SADDLED_BIT);
    }

    public boolean hasBred() {
        return this.getMaskBit(MASK_INDEX, HAS_BRED_BIT);
    }

    public boolean isEating() {
        return this.getMaskBit(MASK_INDEX, EATING_BIT);
    }

    public boolean isRearing() {
        return this.getMaskBit(MASK_INDEX, REARING_BIT);
    }

    public boolean isMouthOpen() {
        return this.getMaskBit(MASK_INDEX, MOUTH_OPEN_BIT);
    }

    public @Nullable UUID getOwner() {
        return super.metadata.getIndex(OWNER_INDEX, null);
    }
}