package mc.replay.nms.entity.metadata.villager;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class WanderingTraderMetadata extends VillagerMetadata {

    public static final int OFFSET = VillagerMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public WanderingTraderMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}