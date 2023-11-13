package mc.replay.nms.entity.metadata.flying;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class GhastMetadata extends FlyingMetadata {

    public static final int OFFSET = FlyingMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int ATTACKING_INDEX = OFFSET;

    public GhastMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setAttacking(boolean value) {
        super.metadata.setIndex(ATTACKING_INDEX, Metadata.Boolean(value));
    }

    public boolean isAttacking() {
        return super.metadata.getIndex(ATTACKING_INDEX, false);
    }
}