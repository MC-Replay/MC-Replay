package mc.replay.nms;

import io.netty.buffer.Unpooled;
import mc.replay.nms.fakeplayer.FakePlayerFilterList;
import mc.replay.nms.fakeplayer.FakePlayerHandler;
import mc.replay.nms.fakeplayer.IRecordingFakePlayer;
import mc.replay.nms.fakeplayer.RecordingFakePlayer_v1_18_R2;
import mc.replay.packetlib.PacketLib;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.utils.ReflectionUtils;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.List;

public final class MCReplayNMS_v1_18_R2 implements MCReplayNMS {

    @SuppressWarnings("unchecked")
    @Override
    public void init() {
        PlayerList playerList = MinecraftServer.getServer().getPlayerList();

        try {
            Field playersField = ReflectionUtils.getField(playerList.getClass(), "j");
            playersField.setAccessible(true);
            List<Object> players = (List<Object>) playersField.get(playerList);

            playersField.set(playerList, new FakePlayerFilterList(this, players));
            playersField.setAccessible(false);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Object getBukkitEntity(Object entity) {
        return ((CraftEntity) entity).getHandle();
    }

    @Override
    public IRecordingFakePlayer createFakePlayer(FakePlayerHandler fakePlayerHandler, Player target) {
        return new RecordingFakePlayer_v1_18_R2(fakePlayerHandler, target);
    }

    @Override
    public ClientboundPacket readPacket(Object packetObject) {
        if (!(packetObject instanceof Packet<?> packet)) return null;

        try {
            int packetId = ConnectionProtocol.PLAY.getPacketId(PacketFlow.CLIENTBOUND, packet);
            if (PacketLib.getPacketRegistry().isClientboundRegistered(packetId)) {
                ByteBuffer buffer = this.serializePacket(packet);

                ReplayByteBuffer byteBuffer = new ReplayByteBuffer(buffer);
                return PacketLib.getPacketRegistry().getClientboundPacket(packetId, byteBuffer);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    private @NotNull ByteBuffer serializePacket(Packet<?> packet) throws IOException {
        FriendlyByteBuf friendlyByteBuf = new FriendlyByteBuf(Unpooled.buffer());
        friendlyByteBuf.writerIndex(0);
        friendlyByteBuf.readerIndex(0);
        packet.write(friendlyByteBuf);

        return ByteBuffer.wrap(friendlyByteBuf.array());
    }
}