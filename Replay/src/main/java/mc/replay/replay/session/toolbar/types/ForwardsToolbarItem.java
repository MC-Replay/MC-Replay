package mc.replay.replay.session.toolbar.types;

import mc.replay.common.utils.item.skull.SkullBuilder;
import mc.replay.replay.session.toolbar.ToolbarItem;

public final class ForwardsToolbarItem extends ToolbarItem {

    public ForwardsToolbarItem(int inventorySlot) {
        super(
                "forwards",
                inventorySlot,
                SkullBuilder.getSkullByURLBuilder(ToolbarItemTextures.FORWARDS)
                        .displayName("&a10s forwards")
                        .lore(
                                "&7Click here to go 10 seconds",
                                "&7forwards."
                        )
                        .build()
        );

        this.onClick = (player) -> {
            // TODO forwards
        };
    }
}