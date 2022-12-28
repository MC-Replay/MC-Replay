package mc.replay.api.recording.recordables.entity;

import mc.replay.api.recording.recordables.Recordable;
import org.jetbrains.annotations.NotNull;

public interface RecordableDefinition<R extends Recordable<?>> {

    int identifier();

    @NotNull Class<R> recordableClass();
}