package mc.replay.nms.inventory;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import mc.replay.nms.NBTConverter_v1_16_R3;
import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;

public final class RItemStack_v1_16_R3 implements RItemStack {

    private static final String MC_REPLAY_TAG = "mc-replay-nbt";

    private final ItemStack itemStack;
    private final org.bukkit.inventory.ItemStack bukkitItemStack;

    private final NBTTagCompound nmsCompoundTag;

    private final CompoundTag compoundTag;

    public RItemStack_v1_16_R3(org.bukkit.inventory.ItemStack bukkitItemSTack, ItemStack itemStack) {
        this.bukkitItemStack = bukkitItemSTack;
        this.itemStack = itemStack;

        this.nmsCompoundTag = this.itemStack.getTag();

        NBTTagCompound rTag = this.nmsCompoundTag == null ? null : this.nmsCompoundTag.getCompound(MC_REPLAY_TAG);
        if (rTag == null || rTag.isEmpty()) {
            this.compoundTag = new CompoundTag(MC_REPLAY_TAG);
            return;
        }

        this.compoundTag = NBTConverter_v1_16_R3.convertFromNMS(MC_REPLAY_TAG, rTag);
    }

    @Override
    public CompoundTag getCompoundTag() {
        return this.compoundTag;
    }

    @Override
    public RItemStack setTag(Tag tag) {
        this.compoundTag.put(tag);
        return this;
    }

    @Override
    public RItemStack removeTag(String tagName) {
        this.compoundTag.remove(tagName);
        return this;
    }

    @Override
    public boolean hasTag(String tagName) {
        return this.compoundTag.contains(tagName);
    }

    @Override
    public Tag getTag(String tagName) {
        return this.compoundTag.get(tagName);
    }


    @Override
    public <T> T getTagValue(String tagName) {
        return this.getTagValue(tagName, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getTagValue(String tagName, T defaultValue) {
        Tag tag = this.getTag(tagName);
        return tag == null ? defaultValue : (T) tag.getValue();
    }

    @Override
    public org.bukkit.inventory.ItemStack bukkit() {
        return CraftItemStack.asBukkitCopy(this.getNMSInstance());
    }

    @Override
    public org.bukkit.inventory.ItemStack originalBukkit() {
        return this.bukkitItemStack;
    }

    @Override
    public ItemStack getNMSInstance() {
        NBTTagCompound compound = this.nmsCompoundTag == null ? new NBTTagCompound() : this.nmsCompoundTag;

        if (!this.compoundTag.isEmpty()) {
            compound.set(MC_REPLAY_TAG, NBTConverter_v1_16_R3.convertToNMS(this.compoundTag));
        }

        this.itemStack.setTag(compound);
        return this.itemStack;
    }

    @Override
    public Object getOriginalNMSInstance() {
        return this.itemStack;
    }
}