package mc.replay.nms.entity.metadata.monster;

import mc.replay.nms.entity.metadata.MobMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class MonsterMetadata extends MobMetadata {

    public static final int OFFSET = MobMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    protected MonsterMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}