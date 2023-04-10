package mc.replay.api.recording.recordables;

import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public interface Recordable<F extends Function<?, ?>> extends ReplayByteBuffer.Writer {

    @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull F function);

    @SuppressWarnings("unchecked")
    default @NotNull List<@NotNull ClientboundPacket> functionlessReplayPackets() {
        return this.createReplayPackets((F) (Function<Object, Object>) o -> null);
    }

    default @Nullable DependentRecordableData<?> depend() {
        return null;
    }
}