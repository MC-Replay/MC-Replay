package mc.replay.api.recording.recordables;

import mc.replay.api.recording.RecordingSession;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public interface Recordable<F extends Function<?, ?>> {

    default @NotNull Function<@NotNull RecordingSession, @NotNull Boolean> shouldRecord() {
        return (session) -> true;
    }

    @NotNull List<@NotNull Object> createReplayPackets(@NotNull F function);

    @SuppressWarnings("unchecked")
    default @NotNull List<@NotNull Object> functionlessReplayPackets() {
        return this.createReplayPackets((F) (Function<Object, Object>) o -> null);
    }
}