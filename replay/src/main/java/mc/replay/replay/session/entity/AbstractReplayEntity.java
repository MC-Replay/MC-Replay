package mc.replay.replay.session.entity;

import lombok.Getter;
import mc.replay.api.replay.session.ReplayPlayer;
import mc.replay.wrapper.entity.EntityWrapper;

import java.util.Collection;

@Getter
public abstract class AbstractReplayEntity<T extends AbstractReplayEntity<T>> {

    protected final int originalEntityId;
    protected EntityWrapper entity;

    public AbstractReplayEntity(int originalEntityId) {
        this.originalEntityId = originalEntityId;
    }

    public int getReplayEntityId() {
        return this.entity.getEntityId();
    }

    public abstract void spawn(Collection<ReplayPlayer> viewers);

    public abstract void destroy(Collection<ReplayPlayer> viewers);
}