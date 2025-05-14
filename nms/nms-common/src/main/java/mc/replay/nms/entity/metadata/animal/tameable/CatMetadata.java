package mc.replay.nms.entity.metadata.animal.tameable;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class CatMetadata extends TameableAnimalMetadata {

    public static final int OFFSET = TameableAnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 4;

    public static final int COLOR_INDEX = OFFSET;
    public static final int LYING_INDEX = OFFSET + 1;
    public static final int RELAXED_INDEX = OFFSET + 2;
    public static final int COLLAR_COLOR_INDEX = OFFSET + 3;

    public CatMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setColor(@NotNull Color value) {
        super.metadata.setIndex(COLOR_INDEX, Metadata.VarInt(value.ordinal()));
    }

    public void setLying(boolean value) {
        super.metadata.setIndex(LYING_INDEX, Metadata.Boolean(value));
    }

    public void setRelaxed(boolean value) {
        super.metadata.setIndex(RELAXED_INDEX, Metadata.Boolean(value));
    }

    public void setCollarColor(int value) {
        super.metadata.setIndex(COLLAR_COLOR_INDEX, Metadata.VarInt(value));
    }

    public @NotNull Color getColor() {
        return Color.VALUES[super.metadata.getIndex(COLOR_INDEX, 1)];
    }

    public boolean isLying() {
        return super.metadata.getIndex(LYING_INDEX, false);
    }

    public boolean isRelaxed() {
        return super.metadata.getIndex(RELAXED_INDEX, false);
    }

    public int getCollarColor() {
        return super.metadata.getIndex(COLLAR_COLOR_INDEX, 14);
    }

    public enum Color {

        TABBY,
        BLACK,
        RED,
        SIAMESE,
        BRITISH_SHORTHAIR,
        CALICO,
        PERSIAN,
        RAGDOLL,
        WHITE,
        JELLIE,
        ALL_BLACK;

        private static final Color[] VALUES = values();
    }
}