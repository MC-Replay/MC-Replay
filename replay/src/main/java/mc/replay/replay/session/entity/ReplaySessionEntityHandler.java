package mc.replay.replay.session.entity;

import lombok.RequiredArgsConstructor;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.common.recordables.types.entity.RecEntityDestroy;
import mc.replay.common.recordables.types.entity.RecEntitySpawn;
import mc.replay.common.recordables.types.entity.RecPlayerDestroy;
import mc.replay.common.recordables.types.entity.RecPlayerSpawn;
import mc.replay.common.replay.IReplayEntityProvider;
import mc.replay.replay.ReplaySession;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public final class ReplaySessionEntityHandler implements IReplayEntityProvider {

    private final ReplaySession replaySession;
    private final Map<Integer, AbstractReplayEntity<?>> entities = new ConcurrentHashMap<>();

    public @Nullable AbstractReplayEntity<?> getEntityByReplayId(int entityId) {
        synchronized (this.entities) {
            for (AbstractReplayEntity<?> entity : this.entities.values()) {
                if (entity.getEntity().getEntityId() == entityId) {
                    return entity;
                }
            }

            return null;
        }
    }

    public @NotNull Collection<ReplayNPC> getNPCs() {
        synchronized (this.entities) {
            Collection<ReplayNPC> npcs = new HashSet<>();

            for (AbstractReplayEntity<?> entity : this.entities.values()) {
                if (entity instanceof ReplayNPC npc) {
                    npcs.add(npc);
                }
            }

            return npcs;
        }
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

            replayNPC.spawn(this, this.replaySession.getAllPlayers());
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
                    recordable.data(),
                    recordable.velocity()
            );

            replayEntity.spawn(this, this.replaySession.getAllPlayers());
            this.entities.put(replayEntity.getOriginalEntityId(), replayEntity);
        }
    }

    @Override
    public void destroyEntity(@NotNull RecEntityDestroy recordable) {
        synchronized (this.entities) {
            for (EntityId entityId : recordable.entityIds()) {
                AbstractReplayEntity<?> entity = this.entities.remove(entityId.entityId());
                if (entity instanceof ReplayEntity) {
                    entity.destroy(this.replaySession.getAllPlayers());
                }
            }
        }
    }

    @Override
    public @Nullable RecordableEntityData getEntity(int originalEntityId) {
        synchronized (this.entities) {
            AbstractReplayEntity<?> replayEntity = this.entities.get(originalEntityId);
            if (replayEntity == null) return null;

            return new RecordableEntityData(
                    replayEntity.getReplayEntityId(),
                    replayEntity.getEntity()
            );
        }
    }
}