package mc.replay.nms;

import io.netty.buffer.Unpooled;
import mc.replay.api.utils.JavaReflections;
import mc.replay.nms.entity.DataWatcherReader_v1_18_R2;
import mc.replay.nms.entity.player.PlayerProfile;
import mc.replay.nms.fakeplayer.FakePlayerFilterList;
import mc.replay.nms.fakeplayer.FakePlayerHandler;
import mc.replay.nms.fakeplayer.IRecordingFakePlayer;
import mc.replay.nms.fakeplayer.RecordingFakePlayer_v1_18_R2;
import mc.replay.nms.inventory.RItemStack;
import mc.replay.nms.inventory.RItemStack_v1_18_R2;
import mc.replay.nms.entity.player.PlayerProfile_v1_18_R2;
import mc.replay.packetlib.PacketLib;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.utils.ReflectionUtils;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class MCReplayNMS_v1_18_R2 implements MCReplayNMS {

    private static final JavaReflections.FieldAccessor<AtomicInteger> ENTITY_ID_COUNTER;

    static {
        ENTITY_ID_COUNTER = JavaReflections.getField(Entity.class, "c", AtomicInteger.class);
    }

    private final DataWatcherReader_v1_18_R2 dataWatcherReader;

    public MCReplayNMS_v1_18_R2() {
        this.dataWatcherReader = new DataWatcherReader_v1_18_R2(this);
    }

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
    public int getCurrentServerTick() {
        return MinecraftServer.currentTick;
    }

    @Override
    public RItemStack modifyItemStack(org.bukkit.inventory.ItemStack itemStack) {
        ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        return new RItemStack_v1_18_R2(itemStack, nmsItemStack);
    }

    @Override
    public int getNewEntityId() {
        return ENTITY_ID_COUNTER.get(null).incrementAndGet();
    }

    @Override
    public Object getBukkitEntity(Object entity) {
        return ((CraftEntity) entity).getHandle();
    }

    @Override
    public Map<Integer, Metadata.Entry<?>> readDataWatcher(org.bukkit.entity.Entity entity) {
        return this.dataWatcherReader.readDataWatcher(entity);
    }

    @Override
    public PlayerProfile getPlayerProfile(Player player) {
        ServerPlayer serverPlayer = (ServerPlayer) this.getBukkitEntity(player);
        return serverPlayer == null ? null : new PlayerProfile_v1_18_R2(serverPlayer.getGameProfile());
    }

    @Override
    public IRecordingFakePlayer createFakePlayer(FakePlayerHandler fakePlayerHandler, Player target) {
        return new RecordingFakePlayer_v1_18_R2(fakePlayerHandler, target);
    }

    @Override
    public void movePlayerSync(Player player, Location to, Runnable callback) {
        MinecraftServer.getServer().execute(() -> {
            ServerPlayer entityPlayer = (ServerPlayer) getBukkitEntity(player);
            entityPlayer.absMoveTo(to.getX(), to.getY(), to.getZ(), to.getYaw(), to.getPitch());

            entityPlayer.getLevel().getChunkSource().move(entityPlayer);
            callback.run();
        });
    }

    @Override
    public ClientboundPacket readPacket(Object packetObject) {
        if (!(packetObject instanceof Packet<?> packet)) return null;

        try {
            Integer packetId = ConnectionProtocol.PLAY.getPacketId(PacketFlow.CLIENTBOUND, packet);
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
        FriendlyByteBuf friendlyByteBuf = new FriendlyByteBuf(Unpooled.buffer());
        friendlyByteBuf.writerIndex(0);
        friendlyByteBuf.readerIndex(0);
        packet.write(friendlyByteBuf);

        return ByteBuffer.wrap(friendlyByteBuf.array());
    }
}