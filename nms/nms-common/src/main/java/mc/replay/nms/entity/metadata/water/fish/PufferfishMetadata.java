package mc.replay.nms.entity.metadata.water.fish;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class PufferfishMetadata extends AbstractFishMetadata {

    public static final int OFFSET = AbstractFishMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int STATE_INDEX = OFFSET;

    public PufferfishMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setState(@NotNull State state) {
        super.metadata.setIndex(STATE_INDEX, Metadata.VarInt(state.ordinal()));
    }

    public @NotNull State getState() {
        return State.VALUES[super.metadata.getIndex(STATE_INDEX, 0)];
    }

    public enum State {

        UNPUFFED,
        SEMI_PUFFED,
        FULLY_PUFFED;

        private static final State[] VALUES = values();
    }
}