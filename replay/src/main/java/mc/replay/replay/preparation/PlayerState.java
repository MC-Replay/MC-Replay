package mc.replay.replay.preparation;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

record PlayerState(ItemStack[] inventoryContents, GameMode gameMode, double health, int foodLevel,
                   float saturation, float exhaustion, int fireTicks, float fallDistance, float exp,
                   int level, int totalExperience, float walkSpeed, float flySpeed, boolean allowFlight,
                   boolean flying) {

    PlayerState(Player player) {
        this(
                player.getInventory().getContents(),
                player.getGameMode(),
                player.getHealth(),
                player.getFoodLevel(),
                player.getSaturation(),
                player.getExhaustion(),
                player.getFireTicks(),
                player.getFallDistance(),
                player.getExp(),
                player.getLevel(),
                player.getTotalExperience(),
                player.getWalkSpeed(),
                player.getFlySpeed(),
                player.getAllowFlight(),
                player.isFlying()
        );
    }

    static final PlayerState DEFAULT_STATE = new PlayerState(
            null,
            GameMode.ADVENTURE,
            20,
            20,
            20,
            0,
            0,
            0,
            0,
            0,
            0,
            0.2f,
            0.1f,
            true,
            true
    );

    void apply(Player player) {
        if (this.inventoryContents == null) {
            player.getInventory().clear();
        } else {
            player.getInventory().setContents(this.inventoryContents);
        }

        player.setGameMode(this.gameMode);
        player.setHealth(this.health);
        player.setFoodLevel(this.foodLevel);
        player.setSaturation(this.saturation);
        player.setExhaustion(this.exhaustion);
        player.setFireTicks(this.fireTicks);
        player.setFallDistance(this.fallDistance);
        player.setExp(this.exp);
        player.setLevel(this.level);
        player.setTotalExperience(this.totalExperience);
        player.setWalkSpeed(this.walkSpeed);
        player.setFlySpeed(this.flySpeed);
        player.setAllowFlight(this.allowFlight);
        player.setFlying(this.flying);
    }
}