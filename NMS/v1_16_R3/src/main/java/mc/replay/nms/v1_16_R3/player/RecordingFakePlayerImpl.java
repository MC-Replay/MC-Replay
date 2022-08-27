package mc.replay.nms.v1_16_R3.player;

import com.mojang.authlib.GameProfile;
import mc.replay.nms.player.RecordingFakePlayer;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import net.minecraft.server.v1_16_R3.PlayerInteractManager;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;

public final class RecordingFakePlayerImpl extends EntityPlayer implements RecordingFakePlayer {

    private final Player target;
    private final FakePlayerNetworkManager fakeNetworkManager;

    public RecordingFakePlayerImpl(Player target) {
        super(
                MinecraftServer.getServer(),
                ((CraftWorld) target.getWorld()).getHandle(),
                new GameProfile(UUID.randomUUID(), target.getName()),
                new PlayerInteractManager(((CraftWorld) target.getWorld()).getHandle())
        );

        this.target = target;

        this.fakeNetworkManager = new FakePlayerNetworkManager(this);
        new PlayerConnection(MinecraftServer.getServer(), this.fakeNetworkManager, this);
    }

    @Override
    public void spawn() {
        Location location = this.target.getLocation().clone();
        this.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        //        this.setInvisible(true);
        this.setSilent(true);
        this.setCustomNameVisible(false);

        //        this.setSpectatorTarget(((CraftPlayer) this.player).getHandle());
        this.server.getPlayerList().a(this.playerConnection.networkManager, this);
        this.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @Override
    public @NotNull Player target() {
        return this.target;
    }

    public void setPacketOutDispatcher(@NotNull Consumer<Object> consumer) {
        this.fakeNetworkManager.setPacketOutDispatcher(consumer);
    }
}