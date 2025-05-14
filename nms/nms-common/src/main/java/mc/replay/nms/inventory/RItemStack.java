package mc.replay.nms.inventory;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import org.bukkit.inventory.ItemStack;

public interface RItemStack {

    CompoundTag getCompoundTag();

    RItemStack setTag(Tag tag);

    RItemStack removeTag(String tagName);

    boolean hasTag(String tagName);

    Tag getTag(String tagName);

    <T> T getTagValue(String tagName);

    <T> T getTagValue(String tagName, T defaultValue);

    ItemStack bukkit();

    ItemStack originalBukkit();

    Object getNMSInstance();

    Object getOriginalNMSInstance();
}