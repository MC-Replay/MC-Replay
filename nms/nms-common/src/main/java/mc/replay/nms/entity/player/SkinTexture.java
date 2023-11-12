package mc.replay.nms.entity.player;

import mc.replay.packetlib.data.PlayerProfileProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record SkinTexture(String value, String signature) {

    public static final String TEXTURES_KEY = "textures";

    public static @Nullable SkinTexture fromProfile(@NotNull PlayerProfile profile) {
        PlayerProfileProperty property = profile.properties().get(TEXTURES_KEY);
        return property == null ? null : new SkinTexture(property.value(), property.signature());
    }
}