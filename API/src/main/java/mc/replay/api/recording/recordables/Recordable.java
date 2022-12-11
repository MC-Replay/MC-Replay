package mc.replay.api.recording.recordables;

import mc.replay.packetlib.network.packet.ClientboundPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface Recordable<F extends Function<?, ?>> {

    @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull F function);

    @SuppressWarnings("unchecked")
    default @NotNull List<@NotNull ClientboundPacket> functionlessReplayPackets() {
        return this.createReplayPackets((F) (Function<Object, Object>) o -> null);
    }
}