package mc.replay.nms;

import com.mojang.authlib.GameProfile;
import org.bukkit.World;

public interface NMSCore {

    Object getCraftWorld(World world);

    Object getWorldServer(Object craftWorld);

    Object createEntityPlayer(Object worldServer, GameProfile gameProfile);
}