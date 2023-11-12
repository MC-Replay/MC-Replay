package mc.replay.nms.entity.metadata.other;

import mc.replay.nms.entity.REntity;
import mc.replay.nms.entity.metadata.EntityMetadata;
import mc.replay.nms.entity.metadata.ObjectDataProvider;
import mc.replay.nms.entity.metadata.ProjectileMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DragonFireballMetadata extends EntityMetadata implements ObjectDataProvider, ProjectileMetadata {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    private REntity shooter;

    public DragonFireballMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    @Override
    public @Nullable REntity getShooter() {
        return this.shooter;
    }

    @Override
    public void setShooter(@Nullable REntity shooter) {
        this.shooter = shooter;
    }

    @Override
    public int getObjectData() {
        return (this.shooter == null) ? 0 : this.shooter.getEntityId();
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return true;
    }
}