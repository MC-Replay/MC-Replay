package mc.replay.replay.session.entity;

import lombok.Getter;
import mc.replay.api.replay.session.IReplayPlayer;
import mc.replay.common.utils.EntityPacketUtils;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.wrapper.data.SkinTexture;
import org.bukkit.World;

import java.util.Collection;
import java.util.Map;

@Getter
public final class ReplayNPC extends AbstractReplayEntity<ReplayNPC> {

    private final String name;
    private final SkinTexture skinTexture;
    private final World replayWorld;
    private final Pos startPosition;

    private final Map<Integer, Metadata.Entry<?>> metadata;

    public ReplayNPC(int originalEntityId, String name, World replayWorld, Pos startPosition,
                     SkinTexture skinTexture, Map<Integer, Metadata.Entry<?>> metadata) {
        super(originalEntityId);

        this.name = name;
        this.skinTexture = skinTexture;
        this.replayWorld = replayWorld;
        this.startPosition = startPosition;
        this.metadata = metadata;
    }

    public ReplayNPC(int originalEntityId, String name, World replayWorld, Pos startPosition, Map<Integer, Metadata.Entry<?>> metadata) {
        this(originalEntityId, name, replayWorld, startPosition, null, metadata);
    }

    @Override
    public void spawn(Collection<IReplayPlayer> viewers) {
        this.entity = EntityPacketUtils.spawnNPC(viewers, this.startPosition, "Suspect", this.skinTexture, metadata);
    }

    @Override
    public void destroy(Collection<IReplayPlayer> viewers) {
        EntityPacketUtils.destroy(viewers, this.entity);
    }
}