package mc.replay.nms;

import io.netty.buffer.Unpooled;
import mc.replay.nms.fakeplayer.FakePlayerFilterList;
import mc.replay.nms.fakeplayer.FakePlayerHandler;
import mc.replay.nms.fakeplayer.IRecordingFakePlayer;
import mc.replay.nms.fakeplayer.RecordingFakePlayer_v1_16_R3;
import mc.replay.nms.inventory.RItemStack;
import mc.replay.nms.inventory.RItemStack_v1_16_R3;
import mc.replay.nms.player.PlayerProfile;
import mc.replay.nms.player.PlayerProfile_v1_16_R3;
import mc.replay.packetlib.PacketLib;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.utils.ReflectionUtils;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.List;

public final class MCReplayNMS_v1_16_R3 implements MCReplayNMS {

    @SuppressWarnings("unchecked")
    @Override
    public void init() {
        PlayerList playerList = MinecraftServer.getServer().getPlayerList();

        try {
            Field playersField = ReflectionUtils.getField(playerList.getClass(), "players");
            playersField.setAccessible(true);
            List<Object> players = (List<Object>) playersField.get(playerList);

            playersField.set(playerList, new FakePlayerFilterList(this, players));
            playersField.setAccessible(false);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public int getCurrentServerTick() {
        return MinecraftServer.currentTick;
    }

    @Override
    public RItemStack modifyItemStack(org.bukkit.inventory.ItemStack itemStack) {
        ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        return new RItemStack_v1_16_R3(itemStack, nmsItemStack);
    }

    @Override
    public Object getBukkitEntity(Object entity) {
        return ((CraftEntity) entity).getHandle();
    }

    @Override
    public PlayerProfile getPlayerProfile(Player player) {
        EntityPlayer entityPlayer = (EntityPlayer) this.getBukkitEntity(player);
        return entityPlayer == null ? null : new PlayerProfile_v1_16_R3(entityPlayer.getProfile());
    }

    @Override
    public IRecordingFakePlayer createFakePlayer(FakePlayerHandler fakePlayerHandler, Player target) {
        return new RecordingFakePlayer_v1_16_R3(fakePlayerHandler, target);
    }

    @Override
    public void movePlayerSync(Player player, Location to, Runnable callback) {
        MinecraftServer.getServer().execute(() -> {
            EntityPlayer entityPlayer = (EntityPlayer) getBukkitEntity(player);
            entityPlayer.setPositionRotation(to.getX(), to.getY(), to.getZ(), to.getYaw(), to.getPitch());

            entityPlayer.getWorldServer().getChunkProvider().movePlayer(entityPlayer);
            callback.run();
        });
    }

    @Override
    public ClientboundPacket readPacket(Object packetObject) {
        if (!(packetObject instanceof Packet<?> packet)) return null;

        try {
            Integer packetId = EnumProtocol.PLAY.a(EnumProtocolDirection.CLIENTBOUND, packet);
            if (packetId != null && PacketLib.getPacketRegistry().isClientboundRegistered(packetId)) {
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
        PacketDataSerializer packetDataSerializer = new PacketDataSerializer(Unpooled.buffer());
        packetDataSerializer.writerIndex(0);
        packetDataSerializer.readerIndex(0);
        packet.b(packetDataSerializer);

        return ByteBuffer.wrap(packetDataSerializer.array());
    }
}