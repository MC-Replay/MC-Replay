package mc.replay.replay.time;

import lombok.AllArgsConstructor;
import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.RecordableDefinition;
import mc.replay.api.recordables.action.EmptyRecordableAction;
import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.action.RecordableAction;
import mc.replay.api.replay.IReplaySession;
import mc.replay.api.replay.time.IReplaySkipTimeHandler;
import mc.replay.api.replay.time.SkipTimeType;
import mc.replay.common.MCReplayInternal;
import mc.replay.common.recordables.actions.internal.InternalEntityRecordableAction;
import mc.replay.common.recordables.types.entity.RecEntityDestroy;
import mc.replay.common.recordables.types.entity.RecEntitySpawn;
import mc.replay.common.recordables.types.entity.RecPlayerDestroy;
import mc.replay.common.recordables.types.entity.RecPlayerSpawn;
import mc.replay.common.recordables.types.entity.movement.RecEntityHeadRotation;
import mc.replay.common.recordables.types.entity.movement.RecEntityPosition;
import mc.replay.common.recordables.types.internal.BlockRelatedRecordable;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.replay.ReplaySession;
import mc.replay.replay.session.entity.AbstractReplayEntity;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@AllArgsConstructor
public final class ReplaySkipTimeHandler implements IReplaySkipTimeHandler {

    private final MCReplayInternal instance;

    @Override
    public void skipTime(@NotNull IReplaySession iReplaySession, int time, @NotNull TimeUnit unit, @NotNull SkipTimeType type) {
        if (!(iReplaySession instanceof ReplaySession session)) {
            throw new IllegalArgumentException("Session is not a MC-Replay ReplaySession");
        }

        int timeMillis = (int) unit.toMillis(time);

        NavigableMap<Integer, List<Recordable>> recordables = session.getRecording().recordables();
        boolean forwards = type == SkipTimeType.FORWARDS;
        int currentTime = session.getPlayTask().getCurrentTime();

        int until = (forwards) ? Math.min(currentTime + timeMillis, recordables.lastKey()) : Math.max(currentTime - timeMillis, recordables.firstKey());
        if (currentTime == until) return; // No need to skip time

        boolean wasPaused = session.isPaused();
        session.setPaused(true);

        NavigableMap<Integer, List<Recordable>> recordablesBetween = recordables.subMap(
                (forwards) ? currentTime : until,
                true,
                (forwards) ? until : currentTime,
                true
        );

        if (!forwards) {
            recordablesBetween = recordablesBetween.descendingMap();
        }

        List<Recordable> blockChangeRecordables = this.findRecordables(recordablesBetween, (recordable) -> recordable instanceof BlockRelatedRecordable);
        List<Recordable> spawnRecordables = this.findRecordables(recordablesBetween, (recordable) -> recordable instanceof RecPlayerSpawn || recordable instanceof RecEntitySpawn);
        List<Recordable> destroyRecordables = this.findRecordables(recordablesBetween, (recordable) -> recordable instanceof RecPlayerDestroy || recordable instanceof RecEntityDestroy);

        this.filterSpawnAndDestroyRecordables(spawnRecordables, destroyRecordables); // Filter out entities that are spawned and destroyed in the skipped time range

        List<ClientboundPacket> packets = new ArrayList<>();
        for (Recordable recordable : blockChangeRecordables) {
            packets.addAll(this.handleRecordableForwards(session, recordable));
        }

        for (Recordable recordable : spawnRecordables) {
            packets.addAll(this.handleRecordableForwards(session, recordable));
        }

        for (Recordable recordable : destroyRecordables) {
            packets.addAll(this.handleRecordableForwards(session, recordable));
        }

        for (AbstractReplayEntity<?> entity : session.getPlayTask().getEntityCache().getEntities().values()) {
            RecEntityPosition position = this.findRecordable(RecEntityPosition.class, until, recordables, (recordable) -> {
                return recordable.entityId().entityId() == entity.getOriginalEntityId();
            });

            RecEntityHeadRotation headRotation = this.findRecordable(RecEntityHeadRotation.class, until, recordables, (recordable) -> {
                return recordable.entityId().entityId() == entity.getOriginalEntityId();
            });

            if (headRotation != null) {
                packets.addAll(this.handleRecordableForwards(session, headRotation));
            }

            if (position != null) {
                packets.addAll(this.handleRecordableForwards(session, position));
            }
        }

        session.getPlayTask().setCurrentTime(until);
        session.getPlayTask().sendPackets(packets);

        if (!wasPaused) {
            session.setPaused(false);
        }
    }

    private <R extends Recordable> R findRecordable(Class<R> clazz, int untilTime, NavigableMap<Integer, List<Recordable>> recordables, Predicate<R> predicate) {
        NavigableMap<Integer, List<Recordable>> recordablesBetween = recordables.subMap(recordables.firstKey(), true, untilTime, true)
                .descendingMap();

        for (Map.Entry<Integer, List<Recordable>> entry : recordablesBetween.entrySet()) {
            for (Recordable recordable : entry.getValue()) {
                if (clazz.isInstance(recordable)) {
                    R casted = clazz.cast(recordable);
                    if (predicate.test(casted)) {
                        return casted;
                    }
                }
            }
        }

        return null;
    }

    private List<Recordable> findRecordables(NavigableMap<Integer, List<Recordable>> recordables, Predicate<Recordable> predicate) {
        List<Recordable> recordablesFound = new ArrayList<>();
        for (Map.Entry<Integer, List<Recordable>> entry : recordables.entrySet()) {
            for (Recordable recordable : entry.getValue()) {
                if (predicate.test(recordable)) {
                    recordablesFound.add(recordable);
                }
            }
        }

        return recordablesFound;
    }

    private void filterSpawnAndDestroyRecordables(List<Recordable> spawnRecordables, List<Recordable> destroyRecordables) {
        spawnRecordables.removeIf((recordable) -> {
            if (recordable instanceof RecPlayerSpawn recPlayerSpawn) {
                return destroyRecordables.removeIf((destroyRecordable) -> {
                    if (destroyRecordable instanceof RecPlayerDestroy recPlayerDestroy) {
                        return recPlayerDestroy.entityId().entityId() == recPlayerSpawn.entityId().entityId();
                    }
                    return false;
                });
            }

            if (recordable instanceof RecEntitySpawn recEntitySpawn) {
                return destroyRecordables.stream().anyMatch((destroyRecordable) -> {
                    if (destroyRecordable instanceof RecEntityDestroy recEntityDestroy) {
                        return recEntityDestroy.entityIds().stream().anyMatch((entityId) -> entityId.entityId() == recEntitySpawn.entityId().entityId());
                    }
                    return false;
                });
            }

            return false;
        });
    }

    @SuppressWarnings("unchecked, rawtypes")
    private Collection<ClientboundPacket> handleRecordableForwards(ReplaySession session, Recordable recordable) {
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