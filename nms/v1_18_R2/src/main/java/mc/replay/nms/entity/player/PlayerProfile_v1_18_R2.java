package mc.replay.nms.entity.player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import mc.replay.nms.entity.player.PlayerProfile;
import mc.replay.packetlib.data.PlayerProfileProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PlayerProfile_v1_18_R2 implements PlayerProfile {

    private final GameProfile gameProfile;
    private final Map<String, PlayerProfileProperty> properties = new HashMap<>();

    public PlayerProfile_v1_18_R2(GameProfile gameProfile) {
        this.gameProfile = gameProfile;

        for (Map.Entry<String, Property> entry : this.gameProfile.getProperties().entries()) {
            this.properties.put(entry.getKey(), new PlayerProfileProperty(
                    entry.getValue().getName(),
                    entry.getValue().getValue(),
                    entry.getValue().getSignature()
            ));
        }
    }

    @Override
    public UUID uuid() {
        return this.gameProfile.getId();
    }

    @Override
    public String name() {
        return this.gameProfile.getName();
    }

    @Override
    public Map<String, PlayerProfileProperty> properties() {
        return this.properties;
    }
}