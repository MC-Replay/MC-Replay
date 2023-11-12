package mc.replay.nms.inventory;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import mc.replay.packetlib.data.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class RItem extends Item {

    private ItemStack itemStack;

    public RItem(int materialId, byte amount, CompoundTag meta) {
        super(materialId, amount, meta);
    }

    public RItem(Item item) {
        this(item.materialId(), item.amount(), item.meta());
    }

    public RItem(ItemStack itemStack) {
        this(of(itemStack));
        this.itemStack = itemStack;
    }

    public @NotNull ItemStack toItemStack() {
        if (this.itemStack != null) return this.itemStack;

        return null;

//        ItemStack itemStack = new ItemStack(this.materialId(), this.amount());
//
//        CompoundTag meta = this.meta();
//        if (meta != null) {
//            ItemSerializer.writeDisplay(itemMeta, meta);
//            ItemSerializer.writeHideFlags(itemMeta, meta);
//            ItemSerializer.writeEnchantments(itemMeta, meta);
//            ItemSerializer.writeItemMeta(itemMeta, meta);
//        }
//
//        return itemStack;
    }

    private static Item of(ItemStack itemStack) {
        return null;
    }
}