package mc.replay.replay.time;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.RecordableDefinition;
import mc.replay.api.recordables.action.EmptyRecordableAction;
import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.action.RecordableAction;
import mc.replay.common.MCReplayInternal;
import mc.replay.common.recordables.actions.internal.InternalEntityRecordableAction;
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

import java.util.*;

final class SkipTimeForwardsHandler extends AbstractSkipTimeHandler {

    SkipTimeForwardsHandler(MCReplayInternal instance) {
        super(instance);
    }

    void skipTime(@NotNull ReplaySession session, int until, @NotNull NavigableMap<Integer, List<Recordable>> recordablesBetween) {
        NavigableMap<Integer, List<Recordable>> recordables = session.getRecording().recordables();

        List<Recordable> blockChangeRecordables = this.findRecordables(recordablesBetween, (recordable) -> recordable instanceof BlockRelatedRecordable);
        List<Recordable> spawnRecordables = this.findRecordables(recordablesBetween, (recordable) -> recordable instanceof RecPlayerSpawn || recordable instanceof RecEntitySpawn);
        List<Recordable> destroyRecordables = this.findRecordables(recordablesBetween, (recordable) -> recordable instanceof RecPlayerDestroy || recordable instanceof RecEntityDestroy);

        this.filterSpawnAndDestroyRecordables(spawnRecordables, destroyRecordables); // Filter out entities that are spawned and destroyed in the skipped time range

        List<ClientboundPacket> packets = new ArrayList<>();
        for (Recordable recordable : blockChangeRecordables) {
            packets.addAll(this.handleRecordable(session, recordable));
        }

        for (Recordable recordable : spawnRecordables) {
            packets.addAll(this.handleRecordable(session, recordable));
        }

        for (Recordable recordable : destroyRecordables) {
            packets.addAll(this.handleRecordable(session, recordable));
        }

        for (AbstractReplayEntity<?> entity : session.getPlayTask().getEntityCache().getEntities().values()) {
            Collection<RecEntitySetPassengers> entityPassengers = this.findLatestTypeUniqueRecordables(RecEntitySetPassengers.class, until, recordables, (recordable) -> {
                return recordable.vehicleEntityid().entityId() == entity.getOriginalEntityId();
            });

            for (RecEntitySetPassengers entityPassenger : entityPassengers) {
                packets.addAll(this.handleRecordable(session, entityPassenger));
            }

            Collection<EntityMovementRecordable> entityMovements = this.findLatestTypeUniqueRecordables(EntityMovementRecordable.class, until, recordables, (recordable) -> {
                return recordable.entityId().entityId() == entity.getOriginalEntityId();
            });

            for (EntityMovementRecordable entityMovement : entityMovements) {
                packets.addAll(this.handleRecordable(session, entityMovement));
            }

            Collection<EntityStateRecordable> entityStates = this.findLatestTypeUniqueRecordables(EntityStateRecordable.class, until, recordables, (recordable) -> {
                return recordable.entityId().entityId() == entity.getOriginalEntityId();
            });

            for (EntityStateRecordable entityState : entityStates) {
                packets.addAll(this.handleRecordable(session, entityState));
            }
        }

        session.getPlayTask().sendPackets(packets);
    }

    @SuppressWarnings("unchecked, rawtypes")
    private Collection<ClientboundPacket> handleRecordable(ReplaySession session, Recordable recordable) {
        RecordableDefinition<? extends Recordable> recordableDefinition = this.instance.getRecordableRegistry().getRecordableDefinition(recordable.getClass());
        if (recordableDefinition == null) return Collections.emptyList();

        RecordableAction<? extends Recordable, ?> action = recordableDefinition.action();
        if (action instanceof InternalEntityRecordableAction internalEntityRecordableAction) {
            return internalEntityRecordableAction.createPacketsForwards(recordable, session.getPlayTask().getEntityCache());
        }

        if (action instanceof EntityRecordableAction entityRecordableAction) {
            return entityRecordableAction.createPacketsForwards(recordable, session.getPlayTask().getEntityCache());
        }

        if (action instanceof EmptyRecordableAction emptyRecordableAction) {
            return emptyRecordableAction.createPacketsForwards(recordable, null);
        }

        return Collections.emptyList();
    }
}