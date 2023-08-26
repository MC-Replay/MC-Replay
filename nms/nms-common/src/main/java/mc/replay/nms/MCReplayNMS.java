package mc.replay.nms;

import mc.replay.nms.fakeplayer.FakePlayerHandler;
import mc.replay.nms.fakeplayer.IRecordingFakePlayer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import org.bukkit.entity.Player;

public interface MCReplayNMS {

    static MCReplayNMS getInstance() {
        return MCReplayNMSInstance.INSTANCE;
    }

    void init();

    Object getBukkitEntity(Object entity);

    IRecordingFakePlayer createFakePlayer(FakePlayerHandler fakePlayerHandler, Player target);

    ClientboundPacket readPacket(Object packet);
}