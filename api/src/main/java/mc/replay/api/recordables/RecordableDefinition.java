package mc.replay.api.recordables;

import mc.replay.api.recordables.action.RecordableAction;
import org.jetbrains.annotations.NotNull;

public interface RecordableDefinition<R extends Recordable> {

    int identifier();

    @NotNull Class<R> recordableClass();

    @NotNull RecordableAction<R, ?> action();
}