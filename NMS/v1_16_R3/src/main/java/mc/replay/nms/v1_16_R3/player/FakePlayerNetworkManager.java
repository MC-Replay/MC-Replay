package mc.replay.nms.v1_16_R3.player;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.Setter;
import net.minecraft.server.v1_16_R3.EnumProtocolDirection;
import net.minecraft.server.v1_16_R3.NetworkManager;
import net.minecraft.server.v1_16_R3.Packet;

import javax.annotation.Nullable;
import java.net.InetSocketAddress;
import java.util.function.Consumer;

@Setter
public final class FakePlayerNetworkManager extends NetworkManager {

    private Consumer<Object> packetOutDispatcher = null;

    public FakePlayerNetworkManager(RecordingFakePlayerImpl recordingPlayer) {
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
        if (this.packetOutDispatcher != null) {
            this.packetOutDispatcher.accept(packet);
        }
    }
}