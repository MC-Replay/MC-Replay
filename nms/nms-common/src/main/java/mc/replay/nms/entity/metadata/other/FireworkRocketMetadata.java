package mc.replay.nms.entity.metadata.other;

import mc.replay.nms.entity.REntity;
import mc.replay.nms.entity.metadata.EntityMetadata;
import mc.replay.nms.entity.metadata.ProjectileMetadata;
import mc.replay.nms.entity.metadata.ShooterProvider;
import mc.replay.nms.inventory.RItem;
import mc.replay.packetlib.data.Item;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FireworkRocketMetadata extends EntityMetadata implements ShooterProvider, ProjectileMetadata {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 3;

    public static final int FIREWORK_INFO_INDEX = OFFSET;
    public static final int SHOOTER_ID_INDEX = OFFSET + 1;
    public static final int SHOT_AT_ANGLE_INDEX = OFFSET + 2;

    private REntity shooter;

    public FireworkRocketMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setFireworkInfo(@NotNull RItem value) {
        super.metadata.setIndex(FIREWORK_INFO_INDEX, Metadata.Slot(value));
    }

    @Override
    public void setShooter(@Nullable REntity shooter) {
        this.shooter = shooter;
        Integer entityId = (shooter == null) ? null : shooter.getEntityId();
        this.setShooterId(entityId);
    }

    @Override
    public @Nullable Integer getShooterId() {
        return super.metadata.getIndex(SHOOTER_ID_INDEX, null);
    }

    @Override
    public void setShooterId(@Nullable Integer shooterId) {
        super.metadata.setIndex(SHOOTER_ID_INDEX, Metadata.OptVarInt(shooterId));
    }

    public void setShotAtAngle(boolean value) {
        super.metadata.setIndex(SHOT_AT_ANGLE_INDEX, Metadata.Boolean(value));
    }

    public @NotNull RItem getFireworkInfo() {
        return super.metadata.getIndex(FIREWORK_INFO_INDEX, new RItem(Item.AIR));
    }

    @Override
    public @Nullable REntity getShooter() {
        return this.shooter;
    }

    public boolean isShotAtAngle() {
        return super.metadata.getIndex(SHOT_AT_ANGLE_INDEX, false);
    }
}