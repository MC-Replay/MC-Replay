package mc.replay.common.recordables;

import lombok.AllArgsConstructor;
import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.RecordableDefinition;
import mc.replay.api.recordables.action.RecordableAction;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

@AllArgsConstructor
final class RecordableDefinitionImpl<R extends Recordable> implements RecordableDefinition<R> {

    private final int identifier;
    private final Class<R> recordableClass;
    private final Function<ReplayByteBuffer, R> recordableConstructor;
    private final RecordableAction<R, ?> action;

    @Override
    public int identifier() {
        return this.identifier;
    }

    @Override
    public @NotNull Class<R> recordableClass() {
        return this.recordableClass;
    }

    @Override
    public @NotNull RecordableAction<R, ?> action() {
        return this.action;
    }

    R construct(@NotNull ReplayByteBuffer reader) {
        return this.recordableConstructor.apply(reader);
    }
}