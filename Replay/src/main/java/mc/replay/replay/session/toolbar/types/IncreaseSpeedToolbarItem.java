package mc.replay.replay.session.toolbar.types;

import mc.replay.common.utils.item.skull.SkullBuilder;
import mc.replay.replay.session.toolbar.ToolbarItem;

public final class IncreaseSpeedToolbarItem extends ToolbarItem {

    public IncreaseSpeedToolbarItem(int inventorySlot) {
        super(
                "increase_speed",
                inventorySlot,
                SkullBuilder.getSkullByURLBuilder(ToolbarItemTextures.INCREASE_SPEED)
                        .displayName("&aIncrease speed")
                        .lore(
                                "&7Click here to increase the",
                                "&7speed of the replay."
                        )
                        .build()
        );

        this.onClick = (player) -> {
            // TODO increase speed
        };
    }
}