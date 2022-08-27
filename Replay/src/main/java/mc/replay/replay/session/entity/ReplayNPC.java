package mc.replay.replay.session.entity;

import com.mojang.authlib.properties.Property;
import lombok.Getter;
import mc.replay.api.replay.session.ReplayPlayer;
import mc.replay.common.utils.EntityPacketUtils;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Collection;

@Getter
public final class ReplayNPC extends AbstractReplayEntity<ReplayNPC> {

    private final String name;
    private final Property skinTexture;
    private final World replayWorld;
    private final Location startLocation;

    public ReplayNPC(int originalEntityId, String name, World replayWorld, Location startLocation, Property skinTexture) {
        super(originalEntityId);

        this.name = name;
        this.skinTexture = skinTexture;
        this.replayWorld = replayWorld;
        this.startLocation = startLocation;
    }

    public ReplayNPC(int originalEntityId, String name, World replayWorld, Location startLocation) {
        this(originalEntityId, name, replayWorld, startLocation, null);
    }

    @Override
    public void spawn(Collection<ReplayPlayer> viewers) {
        Location spawnLocation = new Location(
                this.replayWorld,
                this.startLocation.getX(),
                this.startLocation.getY(),
                this.startLocation.getZ(),
                this.startLocation.getYaw(),
                this.startLocation.getPitch()
        );

        this.entity = EntityPacketUtils.spawnNPC(viewers, spawnLocation, "Suspect", this.skinTexture);
        this.replayEntityId = EntityPacketUtils.getEntityId(this.entity);
    }

    @Override
    public void destroy(Collection<ReplayPlayer> viewers) {
        EntityPacketUtils.destroy(viewers, this.entity);
    }
}