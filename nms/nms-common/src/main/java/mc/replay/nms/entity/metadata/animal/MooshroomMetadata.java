package mc.replay.nms.entity.metadata.animal;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class MooshroomMetadata extends CowMetadata {

    public static final int OFFSET = CowMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int VARIANT_INDEX = OFFSET;

    public MooshroomMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setVariant(@NotNull Variant value) {
        super.metadata.setIndex(VARIANT_INDEX, Metadata.String(value.name().toLowerCase()));
    }

    public @NotNull Variant getVariant() {
        return Variant.valueOf(super.metadata.getIndex(VARIANT_INDEX, "red").toUpperCase());
    }

    public enum Variant {

        RED,
        BROWN
    }
}