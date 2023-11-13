package mc.replay.nms.entity.player;

import mc.replay.nms.MCReplayNMS;
import mc.replay.nms.entity.RLivingEntity;
import mc.replay.nms.entity.metadata.PlayerMetadata;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class RPlayer extends RLivingEntity {

    private PlayerProfile profile;

    private final String username;
    private SkinTexture skin;

    public RPlayer(int entityId, @NotNull PlayerProfile profile) {
        super(EntityType.PLAYER, entityId, profile.uuid());

        this.profile = profile;
        this.username = profile.name();
        this.skin = SkinTexture.fromProfile(profile);
    }

    public RPlayer(PlayerProfile profile) {
        this(MCReplayNMS.getInstance().getNewEntityId(), profile);
    }

    public RPlayer(@NotNull Player player) {
        super(player);

        this.profile = MCReplayNMS.getInstance().getPlayerProfile(player);
        this.username = this.profile.name();
        this.skin = SkinTexture.fromProfile(this.profile);
    }

    public @NotNull PlayerProfile getProfile() {
        return this.profile;
    }

    public @NotNull String getUsername() {
        return this.username;
    }

    public @Nullable SkinTexture getSkin() {
        return this.skin;
    }

    public void setSkin(@NotNull SkinTexture skin) {
        this.skin = skin;
    }

    @Override
    public @NotNull PlayerMetadata getMetadata() {
        return this.getMetaData(PlayerMetadata.class);
    }

    @Override
    public @NotNull RPlayer withUniqueId() {
        return (RPlayer) super.withUniqueId();
    }

    public @NotNull RPlayer withProfile(@NotNull Function<@NotNull PlayerProfile, @NotNull PlayerProfile> function) {
        this.profile = function.apply(this.profile);
        return this;
    }
}