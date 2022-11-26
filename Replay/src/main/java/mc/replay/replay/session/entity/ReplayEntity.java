package mc.replay.replay.session.entity;

import mc.replay.api.replay.session.ReplayPlayer;
import mc.replay.common.utils.EntityPacketUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.Collection;

public final class ReplayEntity extends AbstractReplayEntity<ReplayEntity> {

    private final EntityType entityType;
    private final World replayWorld;
    private final Location startLocation;
    private final Object dataWatcher;
    private final Vector velocity;

    public ReplayEntity(int originalEntityId, EntityType entityType, World replayWorld, Location startLocation, Object dataWatcher, Vector velocity) {
        super(originalEntityId);

        this.entityType = entityType;
        this.replayWorld = replayWorld;
        this.startLocation = startLocation;
        this.dataWatcher = dataWatcher;
        this.velocity = velocity;
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

        this.entity = EntityPacketUtils.spawnEntity(viewers, spawnLocation, this.entityType, this.dataWatcher, this.velocity);
        this.replayEntityId = EntityPacketUtils.getEntityId(this.entity);
    }

    @Override
    public void destroy(Collection<ReplayPlayer> viewers) {
        EntityPacketUtils.destroy(viewers, this.entity);
    }
}