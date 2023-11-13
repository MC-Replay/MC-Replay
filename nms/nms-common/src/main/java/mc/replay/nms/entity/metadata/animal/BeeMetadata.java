package mc.replay.nms.entity.metadata.animal;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class BeeMetadata extends AnimalMetadata {

    public static final int OFFSET = AnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 2;

    public static final int MASK_INDEX = OFFSET;
    public static final int ANGER_TICKS_INDEX = OFFSET + 1;

    public static final byte ANGRY_BIT = 0x02;
    public static final byte HAS_STUNG_BIT = 0x04;
    public static final byte HAS_NECTAR_BIT = 0x08;

    public BeeMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setAngry(boolean value) {
        this.setMaskBit(MASK_INDEX, ANGRY_BIT, value);
    }

    public void setHasStung(boolean value) {
        this.setMaskBit(MASK_INDEX, HAS_STUNG_BIT, value);
    }

    public void setHasNectar(boolean value) {
        this.setMaskBit(MASK_INDEX, HAS_NECTAR_BIT, value);
    }

    public void setAngerTicks(int value) {
        super.metadata.setIndex(ANGER_TICKS_INDEX, MetadataTypes.VarInt(value));
    }

    public boolean isAngry() {
        return this.getMaskBit(MASK_INDEX, ANGRY_BIT);
    }

    public boolean hasStung() {
        return this.getMaskBit(MASK_INDEX, HAS_STUNG_BIT);
    }

    public boolean hasNectar() {
        return this.getMaskBit(MASK_INDEX, HAS_NECTAR_BIT);
    }

    public int getAngerTicks() {
        return super.metadata.getIndex(ANGER_TICKS_INDEX, 0);
    }
}