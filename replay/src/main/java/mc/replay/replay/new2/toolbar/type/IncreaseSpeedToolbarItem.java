package mc.replay.replay.new2.toolbar.type;

import mc.replay.common.utils.item.skull.SkullBuilder;
import mc.replay.replay.new2.toolbar.ToolbarItem;

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
            if (player.replaySession().increaseSpeed()) {
                player.replaySession().updateInformationBar();
            }
        };
    }
}