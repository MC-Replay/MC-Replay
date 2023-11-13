package mc.replay.nms.entity.metadata.monster.raider;

import mc.replay.nms.entity.metadata.monster.MonsterMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class RaiderMetadata extends MonsterMetadata {

    public static final int OFFSET = MonsterMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int CELEBRATING_INDEX = OFFSET;

    protected RaiderMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setCelebrating(boolean value) {
        super.metadata.setIndex(CELEBRATING_INDEX, Metadata.Boolean(value));
    }

    public boolean isCelebrating() {
        return super.metadata.getIndex(CELEBRATING_INDEX, false);
    }
}