package mc.replay.replay.session.entity;

import lombok.RequiredArgsConstructor;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.common.recordables.RecordableOther;
import mc.replay.common.recordables.entity.RecEntityDestroy;
import mc.replay.common.recordables.entity.RecEntitySpawn;
import mc.replay.common.recordables.entity.RecPlayerDestroy;
import mc.replay.common.recordables.entity.RecPlayerSpawn;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.replay.ReplaySessionImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public final class ReplaySessionEntityHandler {

    private final ReplaySessionImpl replaySession;
    private final Map<Integer, AbstractReplayEntity<?>> entities = new HashMap<>();

    public List<ClientboundPacket> handleEntityRecordable(RecordableEntity recordable) {
        return recordable.createReplayPackets((originalEntityId) -> {
            AbstractReplayEntity<?> replayEntity = this.entities.get(originalEntityId);
            if (replayEntity == null) return null;

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
            ReplayEntity replayEntity = new ReplayEntity(
                    recEntitySpawn.entityId().entityId(),
                    recEntitySpawn.entityType(),
                    this.replaySession.getReplayWorld(),
                    recEntitySpawn.location(),
                    recEntitySpawn.dataWatcher(),
                    recEntitySpawn.velocity()
            );

            replayEntity.spawn(this.replaySession.getAllPlayers());
            this.entities.put(replayEntity.getOriginalEntityId(), replayEntity);
        } else if (recordable instanceof RecPlayerDestroy recPlayerDestroy) {
            AbstractReplayEntity<?> entity = this.entities.remove(recPlayerDestroy.entityId().entityId());
            if (entity instanceof ReplayNPC) {
                entity.destroy(this.replaySession.getAllPlayers());
            }
        } else if (recordable instanceof RecEntityDestroy recEntityDestroy) {
            AbstractReplayEntity<?> entity = this.entities.remove(recEntityDestroy.entityId().entityId());
            if (entity instanceof ReplayEntity) {
                entity.destroy(this.replaySession.getAllPlayers());
            }
        }
    }
}