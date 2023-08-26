package mc.replay.nms.fakeplayer;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import mc.replay.api.MCReplayAPI;
import mc.replay.nms.MCReplayNMS;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;

import javax.annotation.Nullable;

public final class RecordingFakePlayerNetworkManager_v1_18_R2 extends Connection implements IRecordingFakePlayerNetworkManager {

    private final RecordingFakePlayer_v1_18_R2 fakePlayer;

    public RecordingFakePlayerNetworkManager_v1_18_R2(RecordingFakePlayer_v1_18_R2 fakePlayer) {
        super(null);

        this.fakePlayer = fakePlayer;
    }

    @Override
    public void send(Packet<?> packet, @Nullable GenericFutureListener<? extends Future<? super Void>> genericfuturelistener) {
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