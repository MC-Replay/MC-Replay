package mc.replay.api.recording.recordables;

import mc.replay.api.recording.recordables.action.RecordableAction;
import org.jetbrains.annotations.NotNull;

public interface RecordableDefinition<R extends Recordable> {

    int identifier();

    @NotNull Class<R> recordableClass();

    @NotNull RecordableAction<R, ?> action();
}