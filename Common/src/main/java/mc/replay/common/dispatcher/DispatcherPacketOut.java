package mc.replay.common.dispatcher;

import mc.replay.common.utils.PacketConverter;

public interface DispatcherPacketOut<T> extends Dispatcher<T> {

    default PacketConverter.ConvertedPacket convert(T packet) {
        return PacketConverter.convert(packet);
    }
}