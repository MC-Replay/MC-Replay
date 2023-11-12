package mc.replay.nms.entity.metadata.monster;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class CreeperMetadata extends MonsterMetadata {

    public static final int OFFSET = MonsterMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 3;

    public static final int STATE_INDEX = OFFSET;
    public static final int CHARGED_INDEX = OFFSET + 1;
    public static final int IGNITED_INDEX = OFFSET + 2;

    public CreeperMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setState(@NotNull State value) {
        super.metadata.setIndex(STATE_INDEX, Metadata.VarInt((value == State.IDLE) ? -1 : 1));
    }

    public void setCharged(boolean value) {
        super.metadata.setIndex(CHARGED_INDEX, Metadata.Boolean(value));
    }

    public void setIgnited(boolean value) {
        super.metadata.setIndex(IGNITED_INDEX, Metadata.Boolean(value));
    }

    public @NotNull State getState() {
        int id = super.metadata.getIndex(STATE_INDEX, -1);
        return (id == -1) ? State.IDLE : State.FUSE;
    }

    public boolean isCharged() {
        return super.metadata.getIndex(CHARGED_INDEX, false);
    }

    public boolean isIgnited() {
        return super.metadata.getIndex(IGNITED_INDEX, false);
    }

    public enum State {

        IDLE,
        FUSE
    }
}