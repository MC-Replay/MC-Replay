package mc.replay.utils.item;

import mc.replay.utils.item.skull.SkullBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public final class ItemBuilder {

    private final ItemStack stack;
    private ItemMeta meta;

    public ItemBuilder(@NotNull ItemStack stack) {
        this.stack = stack;
        this.meta = this.stack.getItemMeta();
    }

    public ItemBuilder(@NotNull ItemStack stack, @NotNull String displayName) {
        this.stack = stack;
        this.meta = this.stack.getItemMeta();

        this.displayName(displayName);
    }

    public ItemBuilder(@NotNull Material material) {
        this.stack = new ItemStack(material);
        this.meta = this.stack.getItemMeta();
    }

    public ItemBuilder(@NotNull Material material, @NotNull String displayName) {
        this.stack = new ItemStack(material);
        this.meta = this.stack.getItemMeta();

        this.displayName(displayName);
    }

    public ItemBuilder meta(@NotNull Consumer<ItemMeta> metaConsumer) {
        metaConsumer.accept(this.meta);
        return this;
    }

    public <T extends ItemMeta> ItemBuilder meta(@NotNull Class<T> metaClass, @NotNull UnaryOperator<T> metaUnaryOperator) {
        this.meta = metaUnaryOperator.apply(metaClass.cast(this.meta));
        return this;
    }

    public <T extends ItemMeta> ItemBuilder metaConsumer(@NotNull Class<T> metaClass, @NotNull Consumer<T> metaConsumer) {
        return this.meta(metaClass, (meta) -> {
            metaConsumer.accept(meta);
            return meta;
        });
    }

    public ItemBuilder displayName(@NotNull String displayName) {
        return this.meta((meta) -> meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName)));
    }

    public ItemBuilder durability(short durability) {
        this.stack.setDurability(durability);
        return this;
    }

    public ItemBuilder material(@NotNull Material material) {
        this.stack.setType(material);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.stack.setAmount(amount);
        return this;
    }

    public ItemBuilder data(byte data) {
        this.stack.getData().setData(data);
        return this;
    }

    public ItemBuilder lore(@NotNull List<String> lore) {
        return this.meta((meta) -> {
            List<String> loreList = new ArrayList<>();

            for (String s : lore) {
                loreList.add(ChatColor.translateAlternateColorCodes('&', s));
            }

            this.meta.setLore(loreList);
        });
    }

    public ItemBuilder lore(@NotNull String... lore) {
        return this.lore(Arrays.asList(lore));
    }

    public ItemBuilder lore(@NotNull UnaryOperator<@NotNull List<String>> loreUnaryOperator) {
        return this.lore(loreUnaryOperator.apply((this.meta.getLore() == null) ? new ArrayList<>() : this.meta.getLore()));
    }

    public ItemBuilder addToLore(@NotNull String line) {
        return this.lore((lore) -> {
            lore.add(ChatColor.translateAlternateColorCodes('&', line));
            return lore;
        });
    }

    public ItemBuilder removeFromLore(@NotNull String line) {
        return this.lore((lore) -> {
            lore.removeIf((loreLine) -> loreLine.equals(ChatColor.translateAlternateColorCodes('&', line)));
            return lore;
        });
    }

    public ItemBuilder removeLore() {
        return this.meta((meta) -> meta.setLore(null));
    }

    public ItemBuilder enchantment(@NotNull Enchantment enchantment, int level) {
        return this.meta((meta) -> meta.addEnchant(enchantment, level, true));
    }

    public ItemBuilder removeEnchantments() {
        return this.meta((meta) -> meta.getEnchants().forEach((enchantment, level) -> meta.removeEnchant(enchantment)));
    }

    public ItemBuilder itemFlags(@NotNull ItemFlag... flags) {
        return this.meta((meta) -> meta.addItemFlags(flags));
    }

    public ItemBuilder removeItemFlags(@NotNull ItemFlag... flags) {
        return this.meta((meta) -> meta.removeItemFlags(flags));
    }

    public ItemBuilder allItemFlags() {
        return this.itemFlags(ItemFlag.values());
    }

    public ItemBuilder removeAllItemFlags() {
        return this.removeItemFlags(ItemFlag.values());
    }

    public ItemBuilder glow(boolean glow) {
        return this.meta((meta) -> {
            if (glow) {
                if (this.stack.getType() == Material.BOW) {
                    meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                } else {
                    meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                }

                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        });
    }

    public ItemBuilder glow() {
        return glow(true);
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        return this.meta((meta) -> meta.setUnbreakable(unbreakable));
    }

    public ItemBuilder unbreakable() {
        return unbreakable(true);
    }

    public ItemBuilder skullTexture(@NotNull String texture, @NotNull UUID uuid) {
        if (this.stack.getType() != SkullBuilder.getSkullMaterial().getType()) return this;
        return this.meta(SkullMeta.class, (meta) -> SkullBuilder.applyTextureToMeta(meta, uuid, texture));
    }

    public ItemBuilder skullTexture(@NotNull String texture) {
        if (this.stack.getType() != SkullBuilder.getSkullMaterial().getType()) return this;
        return this.meta(SkullMeta.class, (meta) -> SkullBuilder.applyTextureToMeta(meta, texture));
    }

    public ItemBuilder skullUrlTexture(@NotNull String url, @NotNull UUID uuid) {
        return this.skullTexture(SkullBuilder.getSkullTextureByUrl(url), uuid);
    }

    public ItemBuilder skullUrlTexture(@NotNull String url) {
        return this.skullTexture(SkullBuilder.getSkullTextureByUrl(url));
    }

    public ItemBuilder skullPlayerTexture(@NotNull String playerName) {
        if (this.stack.getType() != SkullBuilder.getSkullMaterial().getType()) return this;
        return this.meta(SkullMeta.class, (meta) -> SkullBuilder.applyPlayerToMeta(meta, playerName));
    }

    public ItemBuilder skullPlayerTexture(@NotNull OfflinePlayer offlinePlayer) {
        if (offlinePlayer.getName() == null) return this;
        return this.skullPlayerTexture(offlinePlayer.getName());
    }

    public ItemStack build() {
        this.stack.setItemMeta(this.meta);
        return this.stack;
    }

    public String prettifyMaterial(Material material) {
        StringBuilder builder = new StringBuilder();
        String name = material.name().toLowerCase();

        if (name.contains("_")) {
            int arg = 0;
            for (String s : name.split("_")) {
                arg++;
                builder.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1));
                if (arg < name.split("_").length) builder.append(" ");
            }
            return builder.toString();
        }

        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    public String asPreview(ChatColor itemColor) {
        return "&7" + (this.stack.getAmount() > 1 ? this.stack.getAmount() + "x " : "") +
                itemColor + (!this.meta.getDisplayName().equalsIgnoreCase("") ? this.meta.getDisplayName() : prettifyMaterial(this.stack.getType())) + " " +
                "&7" + (!this.stack.getEnchantments().isEmpty() ? "(Enchanted)" : "");
    }
}