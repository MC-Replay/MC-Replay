package mc.replay.nms;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import mc.replay.nms.block.BlockData;
import mc.replay.nms.fakeplayer.FakePlayerHandler;
import mc.replay.nms.fakeplayer.IRecordingFakePlayer;
import mc.replay.nms.inventory.RItemStack;
import mc.replay.nms.entity.player.PlayerProfile;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Map;

public interface MCReplayNMS {

    static MCReplayNMS getInstance() {
        return MCReplayNMSInstance.INSTANCE;
    }

    void init();

    int getCurrentServerTick();

    RItemStack modifyItemStack(ItemStack itemStack);

    int getNewEntityId();

    Entity getBukkitEntity(Object entity);

    Object getNMSEntity(Object entity);

    Map<Integer, Metadata.Entry<?>> readDataWatcher(Entity entity);

    PlayerProfile getPlayerProfile(Player player);

    IRecordingFakePlayer createFakePlayer(FakePlayerHandler fakePlayerHandler, Player target);

    void movePlayerSync(Player player, Location to, Runnable callback);

    ClientboundPacket readPacket(Object packet);

    CompoundTag itemMetaToNBT(ItemStack itemStack);

    ItemMeta itemMetaFromNBT(CompoundTag compoundTag);

    BlockData getBlockData(World world, Vector position);
}