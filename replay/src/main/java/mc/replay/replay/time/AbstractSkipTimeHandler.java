package mc.replay.replay.time;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import mc.replay.api.recordables.Recordable;
import mc.replay.common.MCReplayInternal;
import mc.replay.common.recordables.types.entity.RecEntityDestroy;
import mc.replay.common.recordables.types.entity.RecEntitySpawn;
import mc.replay.common.recordables.types.entity.RecPlayerDestroy;
import mc.replay.common.recordables.types.entity.RecPlayerSpawn;
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

    protected <R extends Recordable> Collection<R> findLatestTypeUniqueRecordables(Class<R> clazz, int untilTime, NavigableMap<Integer, List<Recordable>> recordables, Predicate<R> predicate) {
        NavigableMap<Integer, List<Recordable>> recordablesBetween = recordables.subMap(recordables.firstKey(), true, untilTime, true)
                .descendingMap();

        Map<Class<?>, R> recordablesFound = new HashMap<>();
        for (Map.Entry<Integer, List<Recordable>> entry : recordablesBetween.entrySet()) {
            for (Recordable recordable : entry.getValue()) {
                if (clazz.isAssignableFrom(recordable.getClass()) && !recordablesFound.containsKey(recordable.getClass())) {
                    R casted = clazz.cast(recordable);
                    if (predicate.test(casted)) {
                        recordablesFound.put(casted.getClass(), casted);
                    }
                }
            }
        }

        return Set.copyOf(recordablesFound.values());
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
}