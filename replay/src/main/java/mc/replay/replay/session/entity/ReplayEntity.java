package mc.replay.replay.session.entity;

import mc.replay.api.replay.session.ReplayPlayer;
import mc.replay.common.utils.EntityPacketUtils;
import mc.replay.packetlib.data.Pos;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.Collection;

public final class ReplayEntity extends AbstractReplayEntity<ReplayEntity> {

    private final EntityType entityType;
    private final World replayWorld;
    private final Pos startPosition;
    private final Vector velocity;

    public ReplayEntity(int originalEntityId, EntityType entityType, World replayWorld, Pos startPosition, Vector velocity) {
        super(originalEntityId);

        this.entityType = entityType;
        this.replayWorld = replayWorld;
        this.startPosition = startPosition;
        this.velocity = velocity;
    }

    @Override
    public void spawn(Collection<ReplayPlayer> viewers) {
        this.entity = EntityPacketUtils.spawnEntity(viewers, this.startPosition, this.entityType, this.velocity);
    }

    @Override
    public void destroy(Collection<ReplayPlayer> viewers) {
        EntityPacketUtils.destroy(viewers, this.entity);
    }
}