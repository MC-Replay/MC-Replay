package mc.replay.dispatcher.packet.converters;

import mc.replay.common.recordables.Recordable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ReplayPacketOutConverter<R extends Recordable> {

    @NotNull
    String packetClassName();

    @Nullable
    R recordableFromPacket(Object packet);
}