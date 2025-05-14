package mc.replay.api.recordables.action;

import mc.replay.api.recordables.Recordable;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

@FunctionalInterface
public interface RecordableAction<R extends Recordable, T> {

    @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull R recordable, @UnknownNullability T data);
}