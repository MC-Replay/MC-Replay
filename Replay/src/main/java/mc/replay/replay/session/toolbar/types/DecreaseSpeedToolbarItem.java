package mc.replay.replay.session.toolbar.types;

import mc.replay.common.utils.item.skull.SkullBuilder;
import mc.replay.replay.session.toolbar.ToolbarItem;

public final class DecreaseSpeedToolbarItem extends ToolbarItem {

    public DecreaseSpeedToolbarItem(int inventorySlot) {
        super(
                "decrease_speed",
                inventorySlot,
                SkullBuilder.getSkullByURLBuilder(ToolbarItemTextures.DECREASE_SPEED)
                        .displayName("&aDecrease speed")
                        .lore(
                                "&7Click here to decrease the",
                                "&7speed of the replay."
                        )
                        .build()
        );

        this.onClick = (player) -> {
            if (player.replaySession().decreaseSpeed()) {
                player.replaySession().updateInformationBar();
            }
        };
    }
}