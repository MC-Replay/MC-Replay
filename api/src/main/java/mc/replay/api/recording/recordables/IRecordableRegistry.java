package mc.replay.api.recording.recordables;

import mc.replay.api.recording.recordables.entity.RecordableDefinition;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;

public interface IRecordableRegistry {

    Map<Byte, RecordableDefinition<?>> getPacketRegistry();

    <R extends Recordable<?>> void registerRecordable(byte id,
                                                      @NotNull Class<R> recordableClass,
                                                      @NotNull Function<ReplayByteBuffer, R> recordableConstructor);

    <R extends Recordable<?>> R getRecordable(byte id, @NotNull ReplayByteBuffer buffer);

    <R extends Recordable<?>> byte getRecordableId(@NotNull Class<R> recordableClass);
}