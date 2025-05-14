package mc.replay.nms.entity.metadata.item;

import mc.replay.packetlib.data.entity.Metadata;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class ThrownEnderPearlMetadata extends ItemContainingMetadata {

    public static final int OFFSET = ItemContainingMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public ThrownEnderPearlMetadata(@NotNull Metadata metadata) {
        super(metadata, Material.ENDER_PEARL);
    }
}