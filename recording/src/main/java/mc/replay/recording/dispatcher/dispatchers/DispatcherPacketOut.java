package mc.replay.recording.dispatcher.dispatchers;

import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;

public abstract class DispatcherPacketOut<T extends ClientboundPacket> extends Dispatcher<T> {

    public DispatcherPacketOut(DispatcherHelpers helpers) {
        super(helpers);
    }
}