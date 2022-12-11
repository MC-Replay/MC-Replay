package mc.replay.nms.v1_16_R3.player;

import com.mojang.authlib.GameProfile;
import mc.replay.common.utils.FakePlayerUUID;
import mc.replay.nms.player.RecordingFakePlayer;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.UUID;

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

        this.fakeNetworkManager = new FakePlayerNetworkManager();
        new PlayerConnection(MinecraftServer.getServer(), this.fakeNetworkManager, this);
    }

    @Override
    public void spawn() {
        FakePlayerUUID.UUIDS.add(super.getUniqueID());

        Location location = this.target.getLocation().clone();
        this.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        this.setSilent(true);
        this.setCustomNameVisible(false);
        this.setInvulnerable(false);
        this.setOnGround(false);

        this.server.getPlayerList().a(this.playerConnection.networkManager, this);
        this.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);

        this.playerInteractManager.setGameMode(EnumGamemode.SPECTATOR);
        this.setSpectatorTarget(((CraftPlayer) this.target).getHandle());
    }

    @Override
    public @NotNull Player target() {
        return this.target;
    }

    @Override
    public void setRecording(boolean recording) {
        this.fakeNetworkManager.setRecording(recording);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void tick() {
        if (!this.valid) return;

        if (this.joining) {
            this.joining = false;
        }

        try {
            Field removeQueueField = EntityPlayer.class.getDeclaredField("removeQueue");
            removeQueueField.setAccessible(true);

            Object removeQueueObject = removeQueueField.get(this);
            Deque<Integer> removeQueue = new ArrayDeque<>((Collection<Integer>) removeQueueObject);

            while (!removeQueue.isEmpty()) {
                int size = removeQueue.size();
                int[] entityIdsToDestroy = new int[size];
                int j = 0;

                Integer integer;
                while (j < size && (integer = removeQueue.poll()) != null) {
                    entityIdsToDestroy[j++] = integer;
                }

                this.playerConnection.sendPacket(new PacketPlayOutEntityDestroy(entityIdsToDestroy));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (this.target == null || !this.target.isOnline() || this.target.isDead()) {
            this.getWorldServer().unregisterEntity(this);
        } else {
            Location location = this.target.getLocation().clone();
            this.setLocation(
                    location.getX(),
                    location.getY(),
                    location.getZ(),
                    location.getYaw(),
                    location.getPitch()
            );

            this.getWorldServer().getChunkProvider().movePlayer(this);
        }
    }

    @Override
    public void playerTick() {
    }

    @Override
    public void movementTick() {
    }

    @Override
    protected void tickPotionEffects() {
    }

    @Override
    public void tickWeather() {
    }

    @Override
    protected void doPortalTick() {
    }

    @Override
    protected void doTick() {
    }

    @Override
    public void entityBaseTick() {
    }

    @Override
    public void inactiveTick() {
    }

    @Override
    public void passengerTick() {
    }

    @Override
    public void postTick() {
    }
}