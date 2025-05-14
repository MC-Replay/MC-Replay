package mc.replay.nms.entity.metadata;

import mc.replay.nms.entity.REntity;
import org.jetbrains.annotations.Nullable;

public interface ProjectileMetadata {

    void setShooter(@Nullable REntity shooter);

    @Nullable REntity getShooter();
}