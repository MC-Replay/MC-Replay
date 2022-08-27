package mc.replay.replay.session.entity;

import lombok.Getter;
import mc.replay.api.replay.session.ReplayPlayer;

import java.util.Collection;

@Getter
abstract class AbstractReplayEntity<T extends AbstractReplayEntity<T>> {

    protected final int originalEntityId;
    protected Object entity;
    protected int replayEntityId;

    public AbstractReplayEntity(int originalEntityId) {
        this.originalEntityId = originalEntityId;
    }

    public abstract void spawn(Collection<ReplayPlayer> viewers);

    public abstract void destroy(Collection<ReplayPlayer> viewers);
}