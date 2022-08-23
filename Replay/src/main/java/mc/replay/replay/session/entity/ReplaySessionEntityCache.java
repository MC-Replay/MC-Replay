package mc.replay.replay.session.entity;

import mc.replay.common.recordables.RecordableEntity;
import mc.replay.common.recordables.RecordableOther;
import mc.replay.nms.global.recordable.RecEntityDestroy;
import mc.replay.nms.global.recordable.RecEntitySpawn;
import mc.replay.nms.global.recordable.RecPlayerDestroy;
import mc.replay.nms.global.recordable.RecPlayerSpawn;
import mc.replay.replay.ReplaySessionImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ReplaySessionEntityCache {

    private final ReplaySessionImpl replaySession;
    private final Map<Integer, ReplayEntity<?>> entities = new HashMap<>();

    public ReplaySessionEntityCache(ReplaySessionImpl replaySession) {
        this.replaySession = replaySession;
    }

    public List<Object> handleEntityRecordable(RecordableEntity recordable) {
        return recordable.createReplayPackets((originalEntityId) -> {
            ReplayEntity<?> replayEntity = this.entities.get(originalEntityId);
            if (replayEntity == null) {
                throw new IllegalStateException("Entity not found with original entity id '" + originalEntityId + "'");
            }

            return new RecordableEntity.RecordableEntityData(
                    replayEntity.getReplayEntityId(),
                    replayEntity.getEntity()
            );
        });
    }

    public void handleOtherRecordable(RecordableOther recordable) {
        if (recordable instanceof RecPlayerSpawn recPlayerSpawn) {
            ReplayNPC replayNPC = new ReplayNPC(
                    recPlayerSpawn.entityId().entityId(),
                    recPlayerSpawn.name(),
                    this.replaySession.getReplayWorld(),
                    recPlayerSpawn.location(),
                    recPlayerSpawn.skinTexture()
            );

            replayNPC.spawn(this.replaySession.getAllPlayers());
            this.entities.put(replayNPC.getOriginalEntityId(), replayNPC);
        } else if (recordable instanceof RecEntitySpawn recEntitySpawn) {
            // TODO
        } else if (recordable instanceof RecPlayerDestroy recPlayerDestroy) {
            ReplayEntity<?> entity = this.entities.remove(recPlayerDestroy.entityId().entityId());
            if (entity instanceof ReplayNPC) {
                entity.destroy(this.replaySession.getAllPlayers());
            }
        } else if (recordable instanceof RecEntityDestroy recEntityDestroy) {
            // TODO
        }
    }
}