package mc.replay.api.recording.recordables;

import java.util.function.Function;

public record CachedRecordable(Recordable<? extends Function<?, ?>> recordable) {
}