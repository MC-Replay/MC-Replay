package mc.replay.nms.entity.metadata.other;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.nms.entity.metadata.ObjectDataProvider;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class ShulkerBulletMetadata extends EntityMetadata implements ObjectDataProvider {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public ShulkerBulletMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    @Override
    public int getObjectData() {
        return 0;
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return true;
    }
}