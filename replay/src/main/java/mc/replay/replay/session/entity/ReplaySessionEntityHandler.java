package mc.replay.replay.session.entity;

import lombok.RequiredArgsConstructor;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.api.recording.recordables.entity.RecordableEntityData;
import mc.replay.common.recordables.types.entity.RecEntityDestroy;
import mc.replay.common.recordables.types.entity.RecEntitySpawn;
import mc.replay.common.recordables.types.entity.RecPlayerDestroy;
import mc.replay.common.recordables.types.entity.RecPlayerSpawn;
import mc.replay.common.replay.IReplayEntityProvider;
import mc.replay.packetlib.data.Pos;
import mc.replay.replay.ReplaySession;
import mc.replay.wrapper.entity.EntityWrapper;
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

    @Override
    public void moveEntity(int originalEntityId, @NotNull Pos deltaPosition) {
        synchronized (this.entities) {
            AbstractReplayEntity<?> replayEntity = this.entities.get(originalEntityId);
            if (replayEntity == null) return;

            EntityWrapper entity = replayEntity.getEntity();

            Pos newPosition = entity.getPosition()
                    .add(deltaPosition)
                    .withRotation(deltaPosition.yaw(), deltaPosition.pitch());
            entity.setPosition(newPosition);
        }
    }

    @Override
    public void teleportEntity(int originalEntityId, @NotNull Pos position) {
        synchronized (this.entities) {
            AbstractReplayEntity<?> replayEntity = this.entities.get(originalEntityId);
            if (replayEntity == null) return;

            EntityWrapper entity = replayEntity.getEntity();
            entity.setPosition(position);
        }
    }

    @Override
    public void rotateEntity(int originalEntityId, float yaw, float pitch) {
        synchronized (this.entities) {
            AbstractReplayEntity<?> replayEntity = this.entities.get(originalEntityId);
            if (replayEntity == null) return;

            EntityWrapper entity = replayEntity.getEntity();
            entity.setPosition(entity.getPosition().withRotation(yaw, pitch));
        }
    }
}