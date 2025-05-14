package mc.replay.nms.entity.metadata.water.fish;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TropicalFishMetadata extends AbstractFishMetadata {

    public static final int OFFSET = AbstractFishMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int VARIANT_INDEX = OFFSET;

    public TropicalFishMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public @NotNull Variant getVariant() {
        return getVariantFromId(super.metadata.getIndex(VARIANT_INDEX, 0));
    }

    public void setVariant(@NotNull Variant value) {
        super.metadata.setIndex(VARIANT_INDEX, Metadata.VarInt(getVariantId(value)));
    }

    public static int getVariantId(@NotNull Variant variant) {
        int id = 0;
        id |= variant.patternColor;
        id <<= 8;
        id |= variant.bodyColor;
        id <<= 8;
        id |= variant.pattern.ordinal();
        id <<= 8;
        id |= variant.type.ordinal();
        return id;
    }

    public static @NotNull Variant getVariantFromId(int id) {
        Type type = Type.VALUES[id & 0xFF];
        id >>= 8;
        Pattern pattern = Pattern.VALUES[id & 0xFF];
        id >>= 8;
        byte bodyColor = (byte) (id & 0xFF);
        id >>= 8;
        byte patternColor = (byte) (id & 0xFF);
        return new Variant(type, pattern, bodyColor, patternColor);
    }

    public static class Variant {

        private Type type;
        private Pattern pattern;
        private byte bodyColor;
        private byte patternColor;

        public Variant(@NotNull Type type, @NotNull Pattern pattern, byte bodyColor, byte patternColor) {
            this.type = type;
            this.pattern = pattern;
            this.bodyColor = bodyColor;
            this.patternColor = patternColor;
        }

        public @NotNull Type getType() {
            return type;
        }

        public @NotNull Pattern getPattern() {
            return pattern;
        }

        public byte getBodyColor() {
            return bodyColor;
        }

        public byte getPatternColor() {
            return patternColor;
        }

        public void setType(@NotNull Type type) {
            this.type = type;
        }

        public void setPattern(@NotNull Pattern pattern) {
            this.pattern = pattern;
        }

        public void setBodyColor(byte bodyColor) {
            this.bodyColor = bodyColor;
        }

        public void setPatternColor(byte patternColor) {
            this.patternColor = patternColor;
        }
    }

    public enum Type {

        SMALL,
        LARGE,
        INVISIBLE;

        private static final Type[] VALUES = values();
    }

    public enum Pattern {

        KOB,
        SUNSTREAK,
        SNOOPER,
        DASHER,
        BRINELY,
        SPOTTY,
        NONE;

        private static final Pattern[] VALUES = values();
    }
}