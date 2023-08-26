package mc.replay.nms;

import mc.replay.nms.fakeplayer.FakePlayerHandler;
import mc.replay.nms.fakeplayer.IRecordingFakePlayer;
import mc.replay.nms.inventory.RItemStack;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface MCReplayNMS {

    static MCReplayNMS getInstance() {
        return MCReplayNMSInstance.INSTANCE;
    }

    void init();

    RItemStack modifyItemStack(ItemStack itemStack);

    Object getBukkitEntity(Object entity);

    IRecordingFakePlayer createFakePlayer(FakePlayerHandler fakePlayerHandler, Player target);

    ClientboundPacket readPacket(Object packet);
}