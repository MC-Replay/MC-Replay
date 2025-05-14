package mc.replay.api.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public final class AdventureText {

    private AdventureText() {
    }

    private static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER = LegacyComponentSerializer.builder()
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    private static final GsonComponentSerializer GSON_COMPONENT_SERIALIZER = GsonComponentSerializer.builder()
            .build();

    public static @NotNull Component legacy(@NotNull String text) {
        return LEGACY_COMPONENT_SERIALIZER.deserialize(text);
    }

    public static @NotNull String legacy(@NotNull Component component) {
        return LEGACY_COMPONENT_SERIALIZER.serialize(component);
    }

    public static @NotNull Component gson(@NotNull String text) {
        return GSON_COMPONENT_SERIALIZER.deserialize(text);
    }

    public static @NotNull String gson(@NotNull Component component) {
        return GSON_COMPONENT_SERIALIZER.serialize(component);
    }
}