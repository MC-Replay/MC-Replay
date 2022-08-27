package mc.replay.replay.session.entity;

import mc.replay.api.replay.session.ReplayPlayer;
import mc.replay.common.utils.EntityPacketUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

import java.util.Collection;

public final class ReplayEntity extends AbstractReplayEntity<ReplayEntity> {

    private final EntityType entityType;
    private final World replayWorld;
    private final Location startLocation;

    public ReplayEntity(int originalEntityId, EntityType entityType, World replayWorld, Location startLocation) {
        super(originalEntityId);

        this.entityType = entityType;
        this.replayWorld = replayWorld;
        this.startLocation = startLocation;
    }

    @Override
    public void spawn(Collection<ReplayPlayer> viewers) {
        Location spawnLocation = new Location(
                this.replayWorld,
                this.startLocation.getX(),
                this.startLocation.getY(),
                this.startLocation.getZ(),
                this.startLocation.getYaw(),
                this.startLocation.getPitch()
        );

        this.entity = EntityPacketUtils.spawnEntity(viewers, spawnLocation, this.entityType);
        this.replayEntityId = EntityPacketUtils.getEntityId(this.entity);
    }

    @Override
    public void destroy(Collection<ReplayPlayer> viewers) {
        EntityPacketUtils.destroy(viewers, this.entity);
    }
}