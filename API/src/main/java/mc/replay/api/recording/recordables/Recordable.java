package mc.replay.api.recording.recordables;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public interface Recordable<F extends Function<?, ?>> {

    @NotNull List<@NotNull Object> createReplayPackets(@Nullable F function);
}