package mc.replay.replay.time;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.MCReplayInternal;
import mc.replay.common.recordables.types.entity.RecEntityDestroy;
import mc.replay.common.recordables.types.entity.RecEntitySpawn;
import mc.replay.common.recordables.types.entity.RecPlayerDestroy;
import mc.replay.common.recordables.types.entity.RecPlayerSpawn;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntitySetPassengers;
import mc.replay.common.recordables.types.internal.BlockRelatedRecordable;
import mc.replay.common.recordables.types.internal.EntityMovementRecordable;
import mc.replay.common.recordables.types.internal.EntityStateRecordable;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.replay.ReplaySession;
import mc.replay.replay.session.entity.AbstractReplayEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NavigableMap;

final class JumpTimeForwardsHandler extends AbstractJumpTimeHandler {

    JumpTimeForwardsHandler(MCReplayInternal instance) {
        super(instance);
    }

    void jumpTime(@NotNull ReplaySession session, int until, @NotNull NavigableMap<Integer, List<Recordable>> recordablesBetween) {
        NavigableMap<Integer, List<Recordable>> recordables = session.getRecording().recordables();

        List<Recordable> blockChangeRecordables = super.findRecordables(recordablesBetween, (recordable) -> recordable instanceof BlockRelatedRecordable);
        List<Recordable> spawnRecordables = super.findRecordables(recordablesBetween, (recordable) -> recordable instanceof RecPlayerSpawn || recordable instanceof RecEntitySpawn);
        List<Recordable> destroyRecordables = super.findRecordables(recordablesBetween, (recordable) -> recordable instanceof RecPlayerDestroy || recordable instanceof RecEntityDestroy);

        this.filterSpawnAndDestroyRecordables(spawnRecordables, destroyRecordables); // Filter out entities that are spawned and destroyed in the skipped time range

        List<ClientboundPacket> packets = new ArrayList<>();
        for (Recordable recordable : blockChangeRecordables) {
            packets.addAll(super.handleRecordable(session, recordable));
        }

        for (Recordable recordable : spawnRecordables) {
            packets.addAll(super.handleRecordable(session, recordable));
        }

        for (Recordable recordable : destroyRecordables) {
            packets.addAll(super.handleRecordable(session, recordable));
        }

        for (AbstractReplayEntity<?> entity : session.getEntityCache().getEntities().values()) {
            Collection<RecEntitySetPassengers> entityPassengers = super.findLatestTypeUniqueRecordables(RecEntitySetPassengers.class, until, recordables, (recordable) -> {
                return recordable.vehicleEntityid().entityId() == entity.getOriginalEntityId();
            });

            for (RecEntitySetPassengers entityPassenger : entityPassengers) {
                packets.addAll(super.handleRecordable(session, entityPassenger));
            }

            Collection<EntityMovementRecordable> entityMovements = super.findLatestTypeUniqueRecordables(EntityMovementRecordable.class, until, recordables, (recordable) -> {
                return recordable.entityId().entityId() == entity.getOriginalEntityId();
            });

            for (EntityMovementRecordable entityMovement : entityMovements) {
                packets.addAll(super.handleRecordable(session, entityMovement));
            }

            Collection<EntityStateRecordable> entityStates = super.findLatestTypeUniqueRecordables(EntityStateRecordable.class, until, recordables, (recordable) -> {
                return recordable.entityId().entityId() == entity.getOriginalEntityId();
            });

            for (EntityStateRecordable entityState : entityStates) {
                packets.addAll(super.handleRecordable(session, entityState));
            }
        }

        session.sendPackets(packets);
    }
}