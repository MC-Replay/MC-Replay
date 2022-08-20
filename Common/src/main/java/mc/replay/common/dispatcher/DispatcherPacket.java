package mc.replay.common.dispatcher;

import net.minecraft.server.v1_16_R3.Packet;

public interface DispatcherPacket<T extends Packet> extends Dispatcher<T> {}