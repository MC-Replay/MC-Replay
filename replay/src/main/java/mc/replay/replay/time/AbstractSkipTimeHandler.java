package mc.replay.replay.time;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.replay.ReplaySession;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
abstract class AbstractSkipTimeHandler {

    protected final MCReplayInternal instance;

    abstract void skipTime(@NotNull ReplaySession session, int until, @NotNull NavigableMap<Integer, List<Recordable>> recordablesBetween);

    protected <R extends Recordable> R findRecordable(Class<R> clazz, int untilTime, NavigableMap<Integer, List<Recordable>> recordables, Predicate<R> predicate) {
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

    protected List<Recordable> findRecordables(NavigableMap<Integer, List<Recordable>> recordables, Predicate<Recordable> predicate) {
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

    protected void filterSpawnAndDestroyRecordables(List<Recordable> spawnRecordables, List<Recordable> destroyRecordables) {
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
    protected Collection<ClientboundPacket> handleRecordableForwards(ReplaySession session, Recordable recordable) {
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