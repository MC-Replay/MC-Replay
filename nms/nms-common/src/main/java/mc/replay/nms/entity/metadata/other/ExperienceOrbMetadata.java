package mc.replay.nms.entity.metadata.other;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class ExperienceOrbMetadata extends EntityMetadata {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    private int count = 1;

    public ExperienceOrbMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setCount(int value) {
        this.count = value;
    }

    public int getCount() {
        return this.count;
    }
}