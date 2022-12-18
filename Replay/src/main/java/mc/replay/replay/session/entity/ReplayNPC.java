package mc.replay.replay.session.entity;

import lombok.Getter;
import mc.replay.api.replay.session.ReplayPlayer;
import mc.replay.common.utils.EntityPacketUtils;
import mc.replay.packetlib.data.Pos;
import mc.replay.wrapper.data.SkinTexture;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Collection;

@Getter
public final class ReplayNPC extends AbstractReplayEntity<ReplayNPC> {

    private final String name;
    private final SkinTexture skinTexture;
    private final World replayWorld;
    private final Pos startPosition;

    public ReplayNPC(int originalEntityId, String name, World replayWorld, Pos startPosition, SkinTexture skinTexture) {
        super(originalEntityId);

        this.name = name;
        this.skinTexture = skinTexture;
        this.replayWorld = replayWorld;
        this.startPosition = startPosition;
    }

    public ReplayNPC(int originalEntityId, String name, World replayWorld, Pos startPosition) {
        this(originalEntityId, name, replayWorld, startPosition, null);
    }

    @Override
    public void spawn(Collection<ReplayPlayer> viewers) {
        this.entity = EntityPacketUtils.spawnNPC(viewers, this.startPosition, "Suspect", this.skinTexture);
    }

    @Override
    public void destroy(Collection<ReplayPlayer> viewers) {
        EntityPacketUtils.destroy(viewers, this.entity);
    }
}