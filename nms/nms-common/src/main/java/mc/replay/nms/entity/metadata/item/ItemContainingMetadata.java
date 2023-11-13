package mc.replay.nms.entity.metadata.item;

import mc.replay.nms.entity.metadata.EntityMetadata;
import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.nms.inventory.RItem;
import mc.replay.packetlib.data.entity.Metadata;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

class ItemContainingMetadata extends EntityMetadata {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int ITEM_INDEX = OFFSET;

    private final RItem defaultItem;

    public ItemContainingMetadata(@NotNull Metadata metadata, @NotNull Material defaultItemMaterial) {
        super(metadata);
        this.defaultItem = new RItem(new ItemStack(defaultItemMaterial));
    }

    public void setItem(@NotNull RItem value) {
        super.metadata.setIndex(ITEM_INDEX, MetadataTypes.Slot(value));
    }

    public @NotNull RItem getItem() {
        return super.metadata.getIndex(ITEM_INDEX, this.defaultItem);
    }
}