package mc.replay.nms.entity.metadata.golem;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class SnowGolemMetadata extends AbstractGolemMetadata {

    public static final int OFFSET = AbstractGolemMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int PUMPKIN_HAT_INDEX = OFFSET;

    public static final byte PLAYER_CREATED_BIT = 0x01;

    public SnowGolemMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setHasPumpkinHat(boolean value) {
        super.metadata.setIndex(PUMPKIN_HAT_INDEX, MetadataTypes.Byte((value) ? (byte) 0x10 : (byte) 0x00));
    }

    public boolean hasPumpkinHat() {
        return super.metadata.getIndex(PUMPKIN_HAT_INDEX, (byte) 0x10) == (byte) 0x10;
    }
}