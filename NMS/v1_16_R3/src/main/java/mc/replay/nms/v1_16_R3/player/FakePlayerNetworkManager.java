package mc.replay.nms.v1_16_R3.player;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.Setter;
import mc.replay.api.MCReplayAPI;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.utils.PacketUtils;
import net.minecraft.server.v1_16_R3.EnumProtocolDirection;
import net.minecraft.server.v1_16_R3.NetworkManager;
import net.minecraft.server.v1_16_R3.Packet;

import javax.annotation.Nullable;
import java.net.InetSocketAddress;

@Setter
public final class FakePlayerNetworkManager extends NetworkManager {

    private boolean recording = true;

    public FakePlayerNetworkManager() {
        super(EnumProtocolDirection.SERVERBOUND);

        this.socketAddress = new InetSocketAddress(0);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelhandlercontext, Packet<?> packet) {
    }

    @Override
    public void sendPacket(Packet<?> packet) {
        this.sendPacket(packet, null);
    }

    @Override
    public void sendPacket(Packet<?> packet, @Nullable GenericFutureListener<? extends Future<? super Void>> futureListener) {
        if (this.recording) {
            ClientboundPacket clientboundPacket = PacketUtils.readClientboundPacket(packet);
            if (clientboundPacket != null) {
                MCReplayAPI.getPacketLib().getPacketListener().publishClientbound(clientboundPacket);
            }
        }
    }
}