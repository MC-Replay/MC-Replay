package mc.replay.nms.fakeplayer;

import mc.replay.api.MCReplayAPI;
import mc.replay.nms.MCReplayNMS;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;

import javax.annotation.Nullable;

public final class RecordingFakePlayerNetworkManager_v1_19_R3 extends Connection implements IRecordingFakePlayerNetworkManager {

    private final RecordingFakePlayer_v1_19_R3 fakePlayer;

    public RecordingFakePlayerNetworkManager_v1_19_R3(RecordingFakePlayer_v1_19_R3 fakePlayer) {
        super(null);

        this.fakePlayer = fakePlayer;
    }

    @Override
    public void send(Packet<?> packet, @Nullable PacketSendListener packetsendlistener) {
        if (!this.fakePlayer.isRecording()) return;

        if (packet instanceof ClientboundBundlePacket clientboundBundlePacket) {
            Iterable<Packet<ClientGamePacketListener>> packets = clientboundBundlePacket.subPackets();
            for (Packet<ClientGamePacketListener> subPacket : packets) {
                ClientboundPacket clientboundPacket = MCReplayNMS.getInstance().readPacket(subPacket);
                this.publishPacket(clientboundPacket);
            }

            return;
        }

        ClientboundPacket clientboundPacket = MCReplayNMS.getInstance().readPacket(packet);
        this.publishPacket(clientboundPacket);
    }

    @Override
    public void publishPacket(ClientboundPacket packet) {
        if (packet != null) {
            MCReplayAPI.getPacketLib().packetListener().publishClientbound(this.fakePlayer.target(), packet);
        }
    }
}