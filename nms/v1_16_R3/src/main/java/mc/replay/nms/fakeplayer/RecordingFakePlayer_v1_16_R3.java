package mc.replay.nms.fakeplayer;

import com.mojang.authlib.GameProfile;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityDestroyPacket;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class RecordingFakePlayer_v1_16_R3 extends EntityPlayer implements IRecordingFakePlayer {

    private static final AtomicInteger FAKE_PLAYER_COUNT = new AtomicInteger();

    private final FakePlayerHandler fakePlayerHandler;
    private final RecordingFakePlayerNetworkManager_v1_16_R3 fakeNetworkManager;
    private final Player target;
    private boolean recording = false;

    public RecordingFakePlayer_v1_16_R3(FakePlayerHandler fakePlayerHandler, Player target) {
        super(
                MinecraftServer.getServer(),
                ((CraftWorld) target.getWorld()).getHandle(),
                new GameProfile(UUID.randomUUID(), "RecFakePlayer" + FAKE_PLAYER_COUNT.getAndIncrement()),
                new PlayerInteractManager(((CraftWorld) target.getWorld()).getHandle())
        );

        this.fakePlayerHandler = fakePlayerHandler;
        this.target = target;

        this.fakeNetworkManager = new RecordingFakePlayerNetworkManager_v1_16_R3(this);
        this.playerConnection = new PlayerConnection(MinecraftServer.getServer(), this.fakeNetworkManager, this);
    }

    @Override
    public Player target() {
        return this.target;
    }

    @Override
    public UUID uuid() {
        return super.getUniqueID();
    }

    @Override
    public String name() {
        return super.getName();
    }

    @Override
    public IRecordingFakePlayerNetworkManager fakeNetworkManager() {
        return this.fakeNetworkManager;
    }

    @Override
    public void spawn() {
        this.fakePlayerHandler.addFakePlayer(this);

        Location location = this.target.getLocation().clone();
        super.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        super.server.getPlayerList().players.add(this);
        super.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);

        super.playerInteractManager.setGameMode(EnumGamemode.SPECTATOR);
        super.setSpectatorTarget(((CraftPlayer) this.target).getHandle());
    }

    @Override
    public void remove() {
        super.setSpectatorTarget(null);
        super.getWorldServer().unregisterEntity(this);
        super.server.getPlayerList().players.remove(this);

        this.fakePlayerHandler.removeFakePlayer(this);
    }

    @Override
    public void setRecording(boolean recording) {
        this.recording = recording;
    }

    @Override
    public boolean isRecording() {
        return this.recording;
    }

    @Override
    public void tick() {
        if (!super.valid) return;

        if (super.joining) {
            super.joining = false;
        }

        Deque<Integer> removeQueue = new ArrayDeque<>(super.removeQueue);
        if (!removeQueue.isEmpty()) {
            int size = removeQueue.size();
            List<Integer> entityIdsToDestroy = new ArrayList<>(size);

            Integer integer;
            while ((integer = removeQueue.poll()) != null) {
                entityIdsToDestroy.add(integer);
            }

            this.fakeNetworkManager.publishPacket(new ClientboundEntityDestroyPacket(entityIdsToDestroy));
        }

        if (this.target == null || !this.target.isOnline() || this.target.isDead()) {
            this.remove();
        } else {
            Location location = this.target.getLocation().clone();
            super.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

            super.getWorldServer().getChunkProvider().movePlayer(this);
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