package mc.replay.nms.entity.metadata.animal;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class PandaMetadata extends AnimalMetadata {

    public static final int OFFSET = AnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 6;

    public static final int BREED_TIMER_INDEX = OFFSET;
    public static final int SNEEZE_TIMER_INDEX = OFFSET + 1;
    public static final int EAT_TIMER_INDEX = OFFSET + 2;
    public static final int MAIN_GENE_INDEX = OFFSET + 3;
    public static final int HIDDEN_GENE_INDEX = OFFSET + 4;
    public static final int MASK_INDEX = OFFSET + 5;

    public static final byte SNEEZING_BIT = 0x02;
    public static final byte ROLLING_BIT = 0x04;
    public static final byte SITTING_BIT = 0x08;
    public static final byte ON_BACK_BIT = 0x10;

    public PandaMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setBreedTimer(int value) {
        super.metadata.setIndex(BREED_TIMER_INDEX, Metadata.VarInt(value));
    }

    public void setSneezeTimer(int value) {
        super.metadata.setIndex(SNEEZE_TIMER_INDEX, Metadata.VarInt(value));
    }

    public void setEatTimer(int value) {
        super.metadata.setIndex(EAT_TIMER_INDEX, Metadata.VarInt(value));
    }

    public void setMainGene(@NotNull Gene value) {
        super.metadata.setIndex(MAIN_GENE_INDEX, Metadata.Byte((byte) value.ordinal()));
    }

    public void setHiddenGene(@NotNull Gene value) {
        super.metadata.setIndex(HIDDEN_GENE_INDEX, Metadata.Byte((byte) value.ordinal()));
    }

    public void setSneezing(boolean value) {
        this.setMaskBit(MASK_INDEX, SNEEZING_BIT, value);
    }

    public void setRolling(boolean value) {
        this.setMaskBit(MASK_INDEX, ROLLING_BIT, value);
    }

    public void setSitting(boolean value) {
        this.setMaskBit(MASK_INDEX, SITTING_BIT, value);
    }

    public void setOnBack(boolean value) {
        this.setMaskBit(MASK_INDEX, ON_BACK_BIT, value);
    }

    public int getBreedTimer() {
        return super.metadata.getIndex(BREED_TIMER_INDEX, 0);
    }

    public int getSneezeTimer() {
        return super.metadata.getIndex(SNEEZE_TIMER_INDEX, 0);
    }

    public int getEatTimer() {
        return super.metadata.getIndex(EAT_TIMER_INDEX, 0);
    }

    public @NotNull Gene getMainGene() {
        return Gene.VALUES[super.metadata.getIndex(MAIN_GENE_INDEX, (byte) 0)];
    }

    public @NotNull Gene getHiddenGene() {
        return Gene.VALUES[super.metadata.getIndex(HIDDEN_GENE_INDEX, (byte) 0)];
    }

    public boolean isSneezing() {
        return this.getMaskBit(MASK_INDEX, SNEEZING_BIT);
    }

    public boolean isRolling() {
        return this.getMaskBit(MASK_INDEX, ROLLING_BIT);
    }

    public boolean isSitting() {
        return this.getMaskBit(MASK_INDEX, SITTING_BIT);
    }

    public boolean isOnBack() {
        return this.getMaskBit(MASK_INDEX, ON_BACK_BIT);
    }

    public enum Gene {

        NORMAL,
        AGGRESSIVE,
        LAZY,
        WORRIED,
        PLAYFUL,
        WEAK,
        BROWN;

        private static final Gene[] VALUES = values();
    }
}