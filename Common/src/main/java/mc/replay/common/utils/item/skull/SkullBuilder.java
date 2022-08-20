package mc.replay.common.utils.item.skull;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import mc.replay.common.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

public final class SkullBuilder {

    public static ItemBuilder getSkullByURLBuilder(@NotNull String url, @NotNull UUID uuid) {
        return new ItemBuilder(getSkullMaterial())
                .skullUrlTexture(url, uuid);
    }

    public static ItemBuilder getSkullByURLBuilder(@NotNull String url) {
        return new ItemBuilder(getSkullMaterial())
                .skullUrlTexture(url);
    }

    public static ItemBuilder getSkullByTextureBuilder(@NotNull String texture) {
        return new ItemBuilder(getSkullMaterial())
                .skullTexture(texture);
    }

    public static ItemBuilder getSkullByTextureBuilder(@NotNull String texture, @NotNull UUID uuid) {
        return new ItemBuilder(getSkullMaterial())
                .skullTexture(texture, uuid);
    }

    public static ItemBuilder getSkullByPlayerBuilder(@NotNull String playerName) {
        return new ItemBuilder(getSkullMaterial())
                .skullPlayerTexture(playerName);
    }

    public static ItemBuilder getSkullByPlayerBuilder(@NotNull OfflinePlayer offlinePlayer) {
        return new ItemBuilder(getSkullMaterial())
                .skullPlayerTexture(offlinePlayer);
    }

    public static ItemStack getSkullByURL(@NotNull String url, @NotNull UUID uuid) {
        return getSkullByURLBuilder(url, uuid).build();
    }

    public static ItemStack getSkullByURL(@NotNull String url) {
        return getSkullByURLBuilder(url).build();
    }

    public static ItemStack getSkullByTexture(@NotNull String texture) {
        return getSkullByTextureBuilder(texture).build();
    }

    public static ItemStack getSkullByTexture(@NotNull String texture, @NotNull UUID uuid) {
        return getSkullByTextureBuilder(texture, uuid).build();
    }

    public static ItemStack getSkullByPlayer(@NotNull String playerName) {
        return getSkullByPlayerBuilder(playerName).build();
    }

    public static ItemStack getSkullByPlayer(@NotNull OfflinePlayer offlinePlayer) {
        return getSkullByPlayerBuilder(offlinePlayer).build();
    }

    @Deprecated(forRemoval = true, since = "1.15.5")
    public static ItemStack getSkullByPlayerName(@NotNull String playerName) {
        return getSkullByPlayer(playerName);
    }

    public static ItemStack getSkullMaterial() {
        try {
            return new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short) 3);
        } catch (IllegalArgumentException var1) {
            return new ItemStack(Material.valueOf("PLAYER_HEAD"), 1);
        }
    }

    public static String getSkullTextureByUrl(@NotNull String url) {
        return new String(Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes()));
    }

    public static SkullMeta applyTextureToMeta(@NotNull ItemMeta meta, @NotNull String texture) {
        return applyTextureToMeta(meta, UUID.nameUUIDFromBytes(texture.getBytes()), texture);
    }

    public static SkullMeta applyTextureToMeta(@NotNull ItemMeta meta, @NotNull UUID uuid, @NotNull String texture) {
        SkullMeta skullMeta = (SkullMeta) meta;
        GameProfile profile = new GameProfile(uuid, "custom");
        profile.getProperties().put("textures", new Property("textures", texture));

        try {
            Field fieldProfileItem = skullMeta.getClass().getDeclaredField("profile");
            fieldProfileItem.setAccessible(true);
            fieldProfileItem.set(skullMeta, profile);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return skullMeta;
    }

    public static SkullMeta applyPlayerToMeta(@NotNull ItemMeta meta, @NotNull String playerName) {
        SkullMeta skullMeta = (SkullMeta) meta;
        skullMeta.setOwner(playerName);
        return skullMeta;
    }

    @Deprecated(forRemoval = true)
    public static ItemStack getCustomSkull(String url) {
        return getSkullByURL(url);
    }

    @Deprecated(forRemoval = true)
    public static ItemStack getPlayerSkull(String name) {
        return getSkullByPlayerName(name);
    }
}