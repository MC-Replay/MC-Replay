package mc.replay.nms.entity.metadata.animal.tameable;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.nms.entity.metadata.animal.AnimalMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TameableAnimalMetadata extends AnimalMetadata {

    public static final int OFFSET = AnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 2;

    public static final int MASK_INDEX = OFFSET;
    public static final int OWNER_INDEX = OFFSET + 1;

    public static final byte SITTING_BIT = 0x01;
    public static final byte TAMED_BIT = 0x04;

    protected TameableAnimalMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setSitting(boolean value) {
        this.setMaskBit(MASK_INDEX, SITTING_BIT, value);
    }

    public void setTamed(boolean value) {
        this.setMaskBit(MASK_INDEX, TAMED_BIT, value);
    }

    public void setOwner(@NotNull UUID value) {
        super.metadata.setIndex(OWNER_INDEX, MetadataTypes.OptUUID(value));
    }

    public boolean isSitting() {
        return this.getMaskBit(MASK_INDEX, SITTING_BIT);
    }

    public boolean isTamed() {
        return this.getMaskBit(MASK_INDEX, TAMED_BIT);
    }

    public @NotNull UUID getOwner() {
        return super.metadata.getIndex(OWNER_INDEX, null);
    }
}