package mc.replay.nms.entity.metadata.monster;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class BasePiglinMetadata extends MonsterMetadata {

    public static final int OFFSET = MonsterMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int IMMUNE_TO_ZOMBIFICATION = OFFSET;

    protected BasePiglinMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setImmuneToZombification(boolean value) {
        super.metadata.setIndex(IMMUNE_TO_ZOMBIFICATION, Metadata.Boolean(value));
    }

    public boolean isImmuneToZombification() {
        return super.metadata.getIndex(IMMUNE_TO_ZOMBIFICATION, false);
    }
}