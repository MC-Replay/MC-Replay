package mc.replay.api.recordables;

import mc.replay.api.recordables.action.RecordableAction;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;

public interface IRecordableRegistry {

    Map<Integer, RecordableDefinition<?>> getRecordableRegistry();

    <R extends Recordable> void registerRecordable(int id,
                                                   @NotNull Class<R> recordableClass,
                                                   @NotNull Function<ReplayByteBuffer, R> recordableConstructor,
                                                   @NotNull RecordableAction<R, ?> action);

    <R extends Recordable> R getRecordable(int id, @NotNull ReplayByteBuffer buffer);

    <R extends Recordable> int getRecordableId(@NotNull Class<R> recordableClass);

    <R extends Recordable> RecordableDefinition<R> getRecordableDefinition(@NotNull Class<R> recordableClass);
}