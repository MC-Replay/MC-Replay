package mc.replay.nms.entity.metadata.animal.tameable;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class WolfMetadata extends TameableAnimalMetadata {

    public static final int OFFSET = TameableAnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 3;

    public static final int BEGGING_INDEX = OFFSET;
    public static final int COLLAR_COLOR_INDEX = OFFSET + 1;
    public static final int ANGER_TIME_INDEX = OFFSET + 2;

    public WolfMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setBegging(boolean value) {
        super.metadata.setIndex(BEGGING_INDEX, MetadataTypes.Boolean(value));
    }

    public void setCollarColor(int value) {
        super.metadata.setIndex(COLLAR_COLOR_INDEX, MetadataTypes.VarInt(value));
    }

    public void setAngerTime(int value) {
        super.metadata.setIndex(ANGER_TIME_INDEX, MetadataTypes.VarInt(value));
    }

    public boolean isBegging() {
        return super.metadata.getIndex(BEGGING_INDEX, false);
    }

    public int getCollarColor() {
        return super.metadata.getIndex(COLLAR_COLOR_INDEX, 14);
    }

    public int getAngerTime() {
        return super.metadata.getIndex(ANGER_TIME_INDEX, 0);
    }
}