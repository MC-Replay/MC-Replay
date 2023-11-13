package mc.replay.nms.entity.metadata.arrow;

import mc.replay.nms.entity.REntity;
import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.nms.entity.metadata.ObjectDataProvider;
import mc.replay.nms.entity.metadata.ProjectileMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArrowMetadata extends AbstractArrowMetadata implements ObjectDataProvider, ProjectileMetadata {

    public static final int OFFSET = AbstractArrowMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int COLOR_INDEX = OFFSET;

    private REntity shooter;

    public ArrowMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setColor(int value) {
        super.metadata.setIndex(COLOR_INDEX, MetadataTypes.VarInt(value));
    }

    public int getColor() {
        return super.metadata.getIndex(COLOR_INDEX, -1);
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
        return (this.shooter == null) ? 0 : this.shooter.getEntityId() + 1;
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return true;
    }
}