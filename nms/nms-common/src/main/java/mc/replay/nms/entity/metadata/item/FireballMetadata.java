package mc.replay.nms.entity.metadata.item;

import mc.replay.nms.entity.REntity;
import mc.replay.nms.entity.metadata.ObjectDataProvider;
import mc.replay.nms.entity.metadata.ProjectileMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FireballMetadata extends ItemContainingMetadata implements ObjectDataProvider, ProjectileMetadata {

    public static final int OFFSET = ItemContainingMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    private REntity shooter;

    public FireballMetadata(@NotNull Metadata metadata) {
        super(metadata, Material.AIR);
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