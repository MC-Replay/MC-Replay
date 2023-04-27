package mc.replay.recording.dispatcher.dispatchers;

import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;

public interface DispatcherPacketOut<T extends ClientboundPacket> extends Dispatcher<T> {
}