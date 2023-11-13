package mc.replay.nms.entity.metadata.animal;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class HorseMetadata extends AbstractHorseMetadata {

    public static final int OFFSET = AbstractHorseMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int VARIANT_INDEX = OFFSET;

    public HorseMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setVariant(@NotNull Variant variant) {
        super.metadata.setIndex(VARIANT_INDEX, Metadata.VarInt(getVariantId(variant.marking, variant.color)));
    }

    public @NotNull Variant getVariant() {
        return getVariantFromId(super.metadata.getIndex(VARIANT_INDEX, 0));
    }

    public static int getVariantId(@NotNull Marking marking, @NotNull Color color) {
        return (marking.ordinal() << 8) + color.ordinal();
    }

    public static Variant getVariantFromId(int id) {
        return new Variant(
                Marking.VALUES[id >> 8],
                Color.VALUES[id & 0xFF]
        );
    }

    public static class Variant {

        private Marking marking;
        private Color color;

        public Variant(@NotNull Marking marking, @NotNull Color color) {
            this.marking = marking;
            this.color = color;
        }

        public @NotNull Marking getMarking() {
            return marking;
        }

        public @NotNull Color getColor() {
            return color;
        }

        public void setMarking(@NotNull Marking marking) {
            this.marking = marking;
        }

        public void setColor(@NotNull Color color) {
            this.color = color;
        }
    }

    public enum Marking {

        NONE,
        WHITE,
        WHITE_FIELD,
        WHITE_DOTS,
        BLACK_DOTS;

        private static final Marking[] VALUES = values();
    }

    public enum Color {

        WHITE,
        CREAMY,
        CHESTNUT,
        BROWN,
        BLACK,
        GRAY,
        DARK_BROWN;

        private static final Color[] VALUES = values();
    }
}