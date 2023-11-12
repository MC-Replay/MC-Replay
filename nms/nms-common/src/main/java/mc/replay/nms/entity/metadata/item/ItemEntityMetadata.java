package mc.replay.nms.entity.metadata.item;

import mc.replay.nms.entity.metadata.ObjectDataProvider;
import mc.replay.packetlib.data.entity.Metadata;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class ItemEntityMetadata extends ItemContainingMetadata implements ObjectDataProvider {

    public static final int OFFSET = ItemContainingMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public ItemEntityMetadata(@NotNull Metadata metadata) {
        super(metadata, Material.AIR);
    }

    @Override
    public int getObjectData() {
        return 0;
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return true;
    }
}