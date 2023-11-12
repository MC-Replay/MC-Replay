package mc.replay.nms.entity.metadata.monster;

import mc.replay.nms.entity.REntity;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GuardianMetadata extends MonsterMetadata {

    public static final int OFFSET = MonsterMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 2;

    public static final int RETRACTING_SPIKES_INDEX = OFFSET;
    public static final int TARGET_INDEX = OFFSET + 1;

    private REntity target;

    public GuardianMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setRetractingSpikes(boolean value) {
        super.metadata.setIndex(RETRACTING_SPIKES_INDEX, Metadata.Boolean(value));
    }

    public void setTarget(@Nullable REntity value) {
        this.target = value;
        super.metadata.setIndex(TARGET_INDEX, Metadata.VarInt((value == null) ? 0 : value.getEntityId()));
    }

    public boolean isRetractingSpikes() {
        return super.metadata.getIndex(RETRACTING_SPIKES_INDEX, false);
    }

    public @Nullable REntity getTarget() {
        return this.target;
    }
}