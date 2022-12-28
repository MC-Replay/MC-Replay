package mc.replay.common.recordables;

import lombok.AllArgsConstructor;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.RecordableDefinition;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

@AllArgsConstructor
final class RecordableDefinitionImpl<R extends Recordable<?>> implements RecordableDefinition<R> {

    private final int identifier;
    private final Class<R> recordableClass;
    private final Function<ReplayByteBuffer, R> recordableConstructor;

    @Override
    public int identifier() {
        return this.identifier;
    }

    @Override
    public @NotNull Class<R> recordableClass() {
        return this.recordableClass;
    }

    R construct(@NotNull ReplayByteBuffer reader) {
        return this.recordableConstructor.apply(reader);
    }
}