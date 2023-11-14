package mc.replay.nms.inventory;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import mc.replay.mappings.mapped.MappedMaterial;
import mc.replay.nms.MCReplayNMS;
import mc.replay.packetlib.data.Item;
import mc.replay.packetlib.utils.ProtocolVersion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public final class RItem extends Item {

    private ItemStack itemStack;

    public RItem(int materialId, byte amount, CompoundTag meta) {
        super(materialId, amount, meta);
    }

    public RItem(Item item) {
        this(item.materialId(), item.amount(), item.meta());
    }

    public RItem(ProtocolVersion protocolVersion, ItemStack itemStack) {
        this(of(protocolVersion, itemStack));
        this.itemStack = itemStack;
    }

    public RItem(ItemStack itemStack) {
        this(ProtocolVersion.getServerVersion(), itemStack);
    }

    public @NotNull ItemStack toItemStack(@NotNull ProtocolVersion protocolVersion) {
        if (this.itemStack != null) return this.itemStack;

        MappedMaterial mappedMaterial = new MappedMaterial(protocolVersion, this.materialId());
        ItemStack itemStack = new ItemStack(mappedMaterial.bukkit(), this.amount());

        CompoundTag meta = this.meta();
        ItemMeta itemMeta = MCReplayNMS.getInstance().itemMetaFromNBT(meta);
        itemStack.setItemMeta(itemMeta);

        return this.itemStack = itemStack;
    }

    public @NotNull ItemStack toItemStack() {
        return this.toItemStack(ProtocolVersion.getServerVersion());
    }

    private static Item of(ProtocolVersion protocolVersion, ItemStack itemStack) {
        MappedMaterial mappedMaterial = new MappedMaterial(protocolVersion, itemStack.getType());

        CompoundTag meta = MCReplayNMS.getInstance().itemMetaToNBT(itemStack);
        return new Item(mappedMaterial.mapping().id(), (byte) itemStack.getAmount(), meta);
    }
}