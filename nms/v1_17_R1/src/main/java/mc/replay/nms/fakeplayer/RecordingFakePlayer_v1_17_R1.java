package mc.replay.nms.fakeplayer;

import com.mojang.authlib.GameProfile;
import mc.replay.packetlib.utils.ReflectionUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.GameType;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public final class RecordingFakePlayer_v1_17_R1 extends ServerPlayer implements IRecordingFakePlayer {

    private static final AtomicInteger FAKE_PLAYER_COUNT = new AtomicInteger();

    private final FakePlayerHandler fakePlayerHandler;
    private final RecordingFakePlayerNetworkManager_v1_17_R1 fakeNetworkManager;
    private final Player target;
    private boolean recording = false;

    public RecordingFakePlayer_v1_17_R1(FakePlayerHandler fakePlayerHandler, Player target) {
        super(
                MinecraftServer.getServer(),
                ((CraftWorld) target.getWorld()).getHandle(),
                new GameProfile(UUID.randomUUID(), "RecFakePlayer" + FAKE_PLAYER_COUNT.getAndIncrement())
        );

        this.fakePlayerHandler = fakePlayerHandler;
        this.target = target;

        this.fakeNetworkManager = new RecordingFakePlayerNetworkManager_v1_17_R1(this);
        this.connection = new ServerGamePacketListenerImpl(MinecraftServer.getServer(), this.fakeNetworkManager, this);
    }

    @Override
    public Player target() {
        return this.target;
    }

    @Override
    public UUID uuid() {
        return super.getUUID();
    }

    @Override
    public String name() {
        return super.getGameProfile().getName();
    }

    @Override
    public IRecordingFakePlayerNetworkManager fakeNetworkManager() {
        return this.fakeNetworkManager;
    }

    @Override
    public void spawn() {
        this.fakePlayerHandler.addFakePlayer(this);

        // Set location
        Location location = this.target.getLocation().clone();
        super.absMoveTo(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        // Set isRealPlayer to true so that the tracking works on Paper servers as well
        try {
            Field isRealPlayerField = ReflectionUtils.getField(ServerPlayer.class, "isRealPlayer");
            if (isRealPlayerField != null) {
                isRealPlayerField.set(this, true);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        super.valid = true;

        // Add to server list and world player list
        super.server.getPlayerList().players.add(this);
        super.getLevel().players().add(this);

        // Start tracker
        super.getLevel().getChunkProvider().addEntity(this);

        // Set gamemode and camera
        super.setGameMode(GameType.SPECTATOR);
        super.setCamera(((CraftPlayer) this.target).getHandle());
    }

    @Override
    public void remove() {
        super.setCamera(null);
        super.remove(RemovalReason.DISCARDED);
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

        if (this.target == null || !this.target.isOnline() || this.target.isDead()) {
            this.remove();
        } else {
            Location location = this.target.getLocation().clone();
            super.absMoveTo(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

            super.getLevel().getChunkProvider().move(this);
        }
    }

    @Override
    protected void tickDeath() {
    }

    @Override
    protected void tickEffects() {
    }

    @Override
    public void baseTick() {
    }

    @Override
    public void rideTick() {
    }

    @Override
    public void tickWeather() {
    }

    @Override
    public void doTick() {
    }

    @Override
    public void inactiveTick() {
    }

    @Override
    public void postTick() {
    }
}