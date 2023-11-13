package mc.replay.nms.entity.metadata.other;

import mc.replay.nms.entity.metadata.EntityMetadata;
import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.nms.entity.metadata.ObjectDataProvider;
import mc.replay.packetlib.data.entity.Metadata;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class FallingBlockMetadata extends EntityMetadata implements ObjectDataProvider {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int SPAWN_POSITION_INDEX = OFFSET;

    // TODO change material to block object with stateId and type
    private Material material = Material.STONE;

    public FallingBlockMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setSpawnPosition(@NotNull Vector value) {
        super.metadata.setIndex(SPAWN_POSITION_INDEX, MetadataTypes.Position(value));
    }

    public @NotNull Vector getSpawnPosition() {
        return super.metadata.getIndex(SPAWN_POSITION_INDEX, new Vector(0, 0, 0));
    }

    public void setMaterial(@NotNull Material material) {
        this.material = material;
    }

    public @NotNull Material getMaterial() {
        return this.material;
    }

    @Override
    public int getObjectData() {
        return this.material.ordinal();
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return false;
    }
}