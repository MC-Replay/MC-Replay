package mc.replay.nms;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.Unpooled;
import mc.replay.api.utils.JavaReflections;
import mc.replay.nms.entity.DataWatcherReader_v1_16_R3;
import mc.replay.nms.entity.player.PlayerProfile;
import mc.replay.nms.entity.player.PlayerProfile_v1_16_R3;
import mc.replay.nms.fakeplayer.FakePlayerFilterList;
import mc.replay.nms.fakeplayer.FakePlayerHandler;
import mc.replay.nms.fakeplayer.IRecordingFakePlayer;
import mc.replay.nms.fakeplayer.RecordingFakePlayer_v1_16_R3;
import mc.replay.nms.inventory.ItemNBTTags;
import mc.replay.nms.inventory.RItemStack;
import mc.replay.nms.inventory.RItemStack_v1_16_R3;
import mc.replay.packetlib.PacketLib;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.utils.ReflectionUtils;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class MCReplayNMS_v1_16_R3 implements MCReplayNMS {

    private static final JavaReflections.FieldAccessor<AtomicInteger> ENTITY_ID_COUNTER;

    static {
        ENTITY_ID_COUNTER = JavaReflections.getField(Entity.class, "entityCount", AtomicInteger.class);
    }

    private final DataWatcherReader_v1_16_R3 dataWatcherReader;

    public MCReplayNMS_v1_16_R3() {
        this.dataWatcherReader = new DataWatcherReader_v1_16_R3(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void init() {
        try {
            PlayerList playerList = MinecraftServer.getServer().getPlayerList();
            CraftServer craftServer = (CraftServer) Bukkit.getServer();

            // Get list of players from PlayerList
            Field playersField = ReflectionUtils.getField(PlayerList.class, "players");
            List<Object> players = (List<Object>) playersField.get(playerList);

            // Override CraftServer's playerView field with our own to filter out fake players
            Field playerViewField = ReflectionUtils.getField(CraftServer.class, "playerView");
            playerViewField.set(craftServer, new FakePlayerFilterList(this, players));
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
    public int getNewEntityId() {
        return ENTITY_ID_COUNTER.get(null).incrementAndGet();
    }

    @Override
    public org.bukkit.entity.Entity getBukkitEntity(Object entity) {
        return ((Entity) entity).getBukkitEntity();
    }

    @Override
    public Object getNMSEntity(Object entity) {
        return ((CraftEntity) entity).getHandle();
    }

    @Override
    public Map<Integer, Metadata.Entry<?>> readDataWatcher(org.bukkit.entity.Entity bukkitEntity) {
        return this.dataWatcherReader.readDataWatcher(bukkitEntity);
    }

    @Override
    public PlayerProfile getPlayerProfile(Player player) {
        EntityPlayer entityPlayer = (EntityPlayer) this.getNMSEntity(player);
        return entityPlayer == null ? null : new PlayerProfile_v1_16_R3(entityPlayer.getProfile());
    }

    @Override
    public IRecordingFakePlayer createFakePlayer(FakePlayerHandler fakePlayerHandler, Player target) {
        return new RecordingFakePlayer_v1_16_R3(fakePlayerHandler, target);
    }

    @Override
    public void movePlayerSync(Player player, Location to, Runnable callback) {
        MinecraftServer.getServer().execute(() -> {
            EntityPlayer entityPlayer = (EntityPlayer) getNMSEntity(player);
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

    @Override
    public CompoundTag itemMetaToNBT(org.bukkit.inventory.ItemStack itemStack) {
        ItemStack nmsCopy = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nmsCompoundTag = nmsCopy.getTag();
        if (nmsCompoundTag == null || nmsCompoundTag.isEmpty()) return null;

        CompoundTag compoundTag = NBTConverter_v1_16_R3.convertFromNMS("", nmsCompoundTag.clone());
        ItemNBTTags.cleanCompoundTag(compoundTag);
        return compoundTag;
    }

    @Override
    public ItemMeta itemMetaFromNBT(CompoundTag compoundTag) {
        if (compoundTag == null || compoundTag.isEmpty()) return null;

        NBTTagCompound nmsCompoundTag = NBTConverter_v1_16_R3.convertToNMS(compoundTag);
        ItemStack nmsItemStack = ItemStack.b;
        nmsItemStack.setTag(nmsCompoundTag);
        return CraftItemStack.asBukkitCopy(nmsItemStack).getItemMeta();
    }

    private @NotNull ByteBuffer serializePacket(Packet<?> packet) throws IOException {
        PacketDataSerializer packetDataSerializer = new PacketDataSerializer(Unpooled.buffer());
        packetDataSerializer.writerIndex(0);
        packetDataSerializer.readerIndex(0);
        packet.b(packetDataSerializer);

        return ByteBuffer.wrap(packetDataSerializer.array());
    }
}