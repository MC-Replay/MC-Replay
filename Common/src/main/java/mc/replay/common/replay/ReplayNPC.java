package mc.replay.common.replay;

import com.mojang.authlib.properties.Property;
import lombok.Getter;
import mc.replay.common.utils.EntityPacketUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

@Getter
public final class ReplayNPC extends ReplayEntity<ReplayNPC> {

    private final String name;
    private final Property skinTexture;
    private final Location startLocation;

    public ReplayNPC(int originalEntityId, Collection<Player> viewers, String name, Location startLocation, Property skinTexture) {
        super(originalEntityId, viewers);

        this.name = name;
        this.skinTexture = skinTexture;
        this.startLocation = startLocation;
    }

    public ReplayNPC(int originalEntityId, Collection<Player> viewers, String name, Location startLocation) {
        this(originalEntityId, viewers, name, startLocation, null);
    }

    @Override
    public void spawn(Player viewer) {
        Location spawnLocation = new Location(
                viewer.getWorld(),
                this.startLocation.getX(),
                this.startLocation.getY(),
                this.startLocation.getZ(),
                this.startLocation.getYaw(),
                this.startLocation.getPitch()
        );

        Object entityPlayer = EntityPacketUtils.spawnNPC(viewer, spawnLocation, "Suspect", this.skinTexture);
        this.viewers.put(viewer, entityPlayer);
    }

    @Override
    public void destroy(Player viewer) {
        Object entity = this.viewers.get(viewer);
        if (entity == null) return;

        EntityPacketUtils.destroy(viewer, entity);
    }
}