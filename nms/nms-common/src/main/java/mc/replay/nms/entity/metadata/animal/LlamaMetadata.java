package mc.replay.nms.entity.metadata.animal;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class LlamaMetadata extends ChestedHorseMetadata {

    public static final int OFFSET = ChestedHorseMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 3;

    public static final int STRENGTH_INDEX = OFFSET;
    public static final int CARPET_COLOR_INDEX = OFFSET + 1;
    public static final int VARIANT_INDEX = OFFSET + 2;

    public LlamaMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setStrength(int value) {
        super.metadata.setIndex(STRENGTH_INDEX, Metadata.VarInt(value));
    }

    public void setCarpetColor(int value) {
        super.metadata.setIndex(CARPET_COLOR_INDEX, Metadata.VarInt(value));
    }

    public void setVariant(@NotNull Variant value) {
        super.metadata.setIndex(VARIANT_INDEX, Metadata.VarInt(value.ordinal()));
    }

    public int getStrength() {
        return super.metadata.getIndex(STRENGTH_INDEX, 0);
    }

    public int getCarpetColor() {
        return super.metadata.getIndex(CARPET_COLOR_INDEX, -1);
    }

    public @NotNull Variant getVariant() {
        return Variant.VALUES[super.metadata.getIndex(VARIANT_INDEX, 0)];
    }

    public enum Variant {

        CREAMY,
        WHITE,
        BROWN,
        GRAY;

        private static final Variant[] VALUES = values();
    }
}