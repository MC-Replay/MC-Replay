package mc.replay.nms.entity.metadata.monster.zombie;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.nms.entity.metadata.monster.MonsterMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class ZombieMetadata extends MonsterMetadata {

    public static final int OFFSET = MonsterMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 3;

    public static final int BABY_INDEX = OFFSET;
    public static final int BECOMING_DROWNED_INDEX = OFFSET + 2;

    public ZombieMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setBaby(boolean value) {
        if (this.isBaby() == value) return;
        super.metadata.setIndex(BABY_INDEX, MetadataTypes.Boolean(value));
    }

    public void setBecomingDrowned(boolean value) {
        super.metadata.setIndex(BECOMING_DROWNED_INDEX, MetadataTypes.Boolean(value));
    }

    public boolean isBaby() {
        return super.metadata.getIndex(BABY_INDEX, false);
    }

    public boolean isBecomingDrowned() {
        return super.metadata.getIndex(BECOMING_DROWNED_INDEX, false);
    }
}