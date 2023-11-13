package mc.replay.nms.entity.metadata.animal.tameable;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class ParrotMetadata extends TameableAnimalMetadata {

    public static final int OFFSET = TameableAnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int COLOR_INDEX = OFFSET;

    public ParrotMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setColor(@NotNull Color value) {
        super.metadata.setIndex(COLOR_INDEX, MetadataTypes.VarInt(value.ordinal()));
    }

    public @NotNull Color getColor() {
        return Color.VALUES[super.metadata.getIndex(COLOR_INDEX, 0)];
    }

    public enum Color {

        RED_BLUE,
        BLUE,
        GREEN,
        YELLOW_BLUE,
        GREY;

        private static final Color[] VALUES = values();
    }
}