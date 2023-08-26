package mc.replay.nms.fakeplayer;

import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;

public interface IRecordingFakePlayerNetworkManager {

    void publishPacket(ClientboundPacket packet);
}