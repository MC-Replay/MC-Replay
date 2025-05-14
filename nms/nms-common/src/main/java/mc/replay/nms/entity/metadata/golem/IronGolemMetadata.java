package mc.replay.nms.entity.metadata.golem;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class IronGolemMetadata extends AbstractGolemMetadata {

    public static final int OFFSET = AbstractGolemMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public static final byte PLAYER_CREATED_BIT = 0x01;

    public IronGolemMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setPlayerCreated(boolean value) {
        this.setMaskBit(OFFSET, PLAYER_CREATED_BIT, value);
    }

    public boolean isPlayerCreated() {
        return this.getMaskBit(OFFSET, PLAYER_CREATED_BIT);
    }
}