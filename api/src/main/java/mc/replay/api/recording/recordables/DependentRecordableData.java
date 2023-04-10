package mc.replay.api.recording.recordables;

import java.util.function.Predicate;

public record DependentRecordableData<T>(Class<T> recordableClass, Predicate<T> predicate) {
}