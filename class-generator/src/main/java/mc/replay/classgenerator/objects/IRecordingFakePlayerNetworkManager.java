package mc.replay.classgenerator.objects;

import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;

public interface IRecordingFakePlayerNetworkManager {

    void publishPacket(ClientboundPacket packet);
}