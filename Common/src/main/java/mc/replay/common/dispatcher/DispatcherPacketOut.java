package mc.replay.common.dispatcher;

import mc.replay.packetlib.network.packet.ClientboundPacket;

public interface DispatcherPacketOut<T extends ClientboundPacket> extends Dispatcher<T> {
}