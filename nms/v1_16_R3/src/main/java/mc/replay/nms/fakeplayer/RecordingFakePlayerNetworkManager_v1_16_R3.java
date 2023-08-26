package mc.replay.nms.fakeplayer;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import mc.replay.api.MCReplayAPI;
import mc.replay.nms.MCReplayNMS;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import net.minecraft.server.v1_16_R3.NetworkManager;
import net.minecraft.server.v1_16_R3.Packet;

import javax.annotation.Nullable;

public final class RecordingFakePlayerNetworkManager_v1_16_R3 extends NetworkManager implements IRecordingFakePlayerNetworkManager {

    private final RecordingFakePlayer_v1_16_R3 fakePlayer;

    public RecordingFakePlayerNetworkManager_v1_16_R3(RecordingFakePlayer_v1_16_R3 fakePlayer) {
        super(null);

        this.fakePlayer = fakePlayer;
    }

    @Override
    public void sendPacket(Packet<?> packet, @Nullable GenericFutureListener<? extends Future<? super Void>> genericfuturelistener) {
        if (!this.fakePlayer.isRecording()) return;

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