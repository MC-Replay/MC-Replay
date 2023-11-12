package mc.replay.nms.entity.metadata.other;

import mc.replay.nms.entity.REntity;
import mc.replay.nms.entity.metadata.EntityMetadata;
import mc.replay.nms.entity.metadata.ObjectDataProvider;
import mc.replay.nms.entity.metadata.ProjectileMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WitherSkullMetadata extends EntityMetadata implements ObjectDataProvider, ProjectileMetadata {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int INVULNERABLE_INDEX = OFFSET;

    private REntity shooter;

    public WitherSkullMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setInvulnerable(boolean value) {
        super.metadata.setIndex(INVULNERABLE_INDEX, Metadata.Boolean(value));
    }

    public boolean isInvulnerable() {
        return super.metadata.getIndex(INVULNERABLE_INDEX, false);
    }

    @Override
    public void setShooter(@Nullable REntity shooter) {
        this.shooter = shooter;
    }

    @Override
    public @Nullable REntity getShooter() {
        return this.shooter;
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