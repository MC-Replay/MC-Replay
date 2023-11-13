package mc.replay.mappings;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public record MappingKey(String key) {

    public MappingKey {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        if (key.startsWith("minecraft:")) {
            key = key.split("\\:")[1];
        }
    }

    public @NotNull NamespacedKey toMinecraftNamespacedKey() {
        return NamespacedKey.minecraft(this.key);
    }

    public static @NotNull MappingKey from(@NotNull NamespacedKey key) {
        return new MappingKey(key.getKey());
    }

    public static @NotNull MappingKey from(@NotNull String key) {
        return new MappingKey(key);
    }
}