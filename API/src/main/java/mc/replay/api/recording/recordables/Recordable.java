package mc.replay.api.recording.recordables;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface Recordable<F extends Function<?, ?>> {

    @NotNull List<@NotNull Object> createReplayPackets(@NotNull F function);

    @SuppressWarnings("unchecked")
    default @NotNull List<@NotNull Object> functionlessReplayPackets() {
        return this.createReplayPackets((F) (Function<Object, Object>) o -> null);
    }
}