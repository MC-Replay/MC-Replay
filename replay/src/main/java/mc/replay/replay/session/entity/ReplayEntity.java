package mc.replay.replay.session.entity;

import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.replay.session.IReplayPlayer;
import mc.replay.replay.utils.EntityPacketUtils;
import mc.replay.packetlib.data.Pos;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.Collection;

public final class ReplayEntity extends AbstractReplayEntity<ReplayEntity> {

    private final EntityType entityType;
    private final World replayWorld;
    private final Pos startPosition;
    private final int data;
    private final Vector velocity;

    public ReplayEntity(int originalEntityId, EntityType entityType, World replayWorld, Pos startPosition, int data, Vector velocity) {
        super(originalEntityId);

        this.entityType = entityType;
        this.replayWorld = replayWorld;
        this.startPosition = startPosition;
        this.data = data;
        this.velocity = velocity;
    }

    @Override
    public void spawn(IEntityProvider provider, Collection<IReplayPlayer> viewers) {
        this.entity = EntityPacketUtils.spawnEntity(provider, viewers, this.startPosition, this.entityType, this.data, this.velocity);
    }

    @Override
    public void destroy(Collection<IReplayPlayer> viewers) {
        EntityPacketUtils.destroy(viewers, this.entity);
    }
}