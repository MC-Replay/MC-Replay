package mc.replay.api.recording.recordables;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public record DependentRecordableData<T>(@NotNull Class<T> recordableClass, @NotNull Predicate<@NotNull T> predicate) {
}