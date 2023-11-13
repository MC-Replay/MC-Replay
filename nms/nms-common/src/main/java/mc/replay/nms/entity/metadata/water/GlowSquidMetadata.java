package mc.replay.nms.entity.metadata.water;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class GlowSquidMetadata extends WaterAnimalMetadata {

    public static final int OFFSET = WaterAnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int DARK_TICKS_REMAINING_INDEX = OFFSET;

    public GlowSquidMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setDarkTicksRemaining(int value) {
        super.metadata.setIndex(DARK_TICKS_REMAINING_INDEX, MetadataTypes.VarInt(value));
    }

    public int getDarkTicksRemaining() {
        return super.metadata.getIndex(DARK_TICKS_REMAINING_INDEX, 0);
    }
}