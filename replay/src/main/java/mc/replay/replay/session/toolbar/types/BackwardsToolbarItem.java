package mc.replay.replay.session.toolbar.types;

import mc.replay.common.utils.item.skull.SkullBuilder;
import mc.replay.replay.session.toolbar.ToolbarItem;

public final class BackwardsToolbarItem extends ToolbarItem {

    public BackwardsToolbarItem(int inventorySlot) {
        super(
                "backwards",
                inventorySlot,
                SkullBuilder.getSkullByURLBuilder(ToolbarItemTextures.BACKWARDS)
                        .displayName("&a10s backwards")
                        .lore(
                                "&7Click here to go 10 seconds",
                                "&7backwards."
                        )
                        .build()
        );

        this.onClick = (player) -> {
            // TODO backwards
        };
    }
}