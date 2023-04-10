package mc.replay.replay.session.entity;

import lombok.RequiredArgsConstructor;
import mc.replay.api.recording.recordables.entity.RecordableEntityData;
import mc.replay.common.recordables.types.entity.RecEntityDestroy;
import mc.replay.common.recordables.types.entity.RecEntitySpawn;
import mc.replay.common.recordables.types.entity.RecPlayerDestroy;
import mc.replay.common.recordables.types.entity.RecPlayerSpawn;
import mc.replay.common.replay.IReplayEntityProcessor;
import mc.replay.replay.ReplaySessionImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@RequiredArgsConstructor
public final class ReplaySessionEntityHandler implements IReplayEntityProcessor {

    private final ReplaySessionImpl replaySession;
    private final Map<Integer, AbstractReplayEntity<?>> entities = new ConcurrentHashMap<>();

    public Function<Integer, RecordableEntityData> createEntityGetterFunction() {
        return (originalEntityId) -> {
            AbstractReplayEntity<?> replayEntity = this.entities.get(originalEntityId);
            if (replayEntity == null) return null;

            return new RecordableEntityData(
                    replayEntity.getReplayEntityId(),
                    replayEntity.getEntity()
            );
        };
    }

    @Override
    public void spawnPlayer(@NotNull RecPlayerSpawn recordable) {
        synchronized (this.entities) {
            ReplayNPC replayNPC = new ReplayNPC(
                    recordable.entityId().entityId(),
                    recordable.name(),
                    this.replaySession.getReplayWorld(),
                    recordable.position(),
                    recordable.skinTexture(),
                    recordable.metadata()
            );

            replayNPC.spawn(this.replaySession.getAllPlayers());
            this.entities.put(replayNPC.getOriginalEntityId(), replayNPC);
        }
    }

    @Override
    public void destroyPlayer(@NotNull RecPlayerDestroy recordable) {
        synchronized (this.entities) {
            AbstractReplayEntity<?> entity = this.entities.remove(recordable.entityId().entityId());
            if (entity instanceof ReplayNPC) {
                entity.destroy(this.replaySession.getAllPlayers());
            }
        }
    }

    @Override
    public void spawnEntity(@NotNull RecEntitySpawn recordable) {
        synchronized (this.entities) {
            ReplayEntity replayEntity = new ReplayEntity(
                    recordable.entityId().entityId(),
                    recordable.entityType(),
                    this.replaySession.getReplayWorld(),
                    recordable.position(),
                    recordable.velocity()
            );

            replayEntity.spawn(this.replaySession.getAllPlayers());
            this.entities.put(replayEntity.getOriginalEntityId(), replayEntity);
        }
    }

    @Override
    public void destroyEntity(@NotNull RecEntityDestroy recordable) {
        synchronized (this.entities) {
            AbstractReplayEntity<?> entity = this.entities.remove(recordable.entityId().entityId());
            if (entity instanceof ReplayEntity) {
                entity.destroy(this.replaySession.getAllPlayers());
            }
        }
    }
}