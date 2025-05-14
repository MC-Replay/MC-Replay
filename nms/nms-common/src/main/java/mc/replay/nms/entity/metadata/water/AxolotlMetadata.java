package mc.replay.nms.entity.metadata.water;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class AxolotlMetadata extends WaterAnimalMetadata {

    public static final int OFFSET = WaterAnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 3;

    public static final int VARIANT_INDEX = OFFSET;
    public static final int PLAYING_DEAD_INDEX = OFFSET + 1;
    public static final int FROM_BUCKET_INDEX = OFFSET + 2;

    public AxolotlMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setVariant(@NotNull Variant variant) {
        super.metadata.setIndex(VARIANT_INDEX, Metadata.VarInt(variant.ordinal()));
    }

    public void setPlayingDead(boolean playingDead) {
        super.metadata.setIndex(PLAYING_DEAD_INDEX, Metadata.Boolean(playingDead));
    }

    public void setFromBucket(boolean fromBucket) {
        super.metadata.setIndex(FROM_BUCKET_INDEX, Metadata.Boolean(fromBucket));
    }

    public @NotNull Variant getVariant() {
        return Variant.VALUES[super.metadata.getIndex(VARIANT_INDEX, 0)];
    }

    public boolean isPlayingDead() {
        return super.metadata.getIndex(PLAYING_DEAD_INDEX, false);
    }

    public boolean isFromBucket() {
        return super.metadata.getIndex(FROM_BUCKET_INDEX, false);
    }

    public enum Variant {

        LUCY,
        WILD,
        GOLD,
        CYAN,
        BLUE;

        private static final Variant[] VALUES = values();
    }
}