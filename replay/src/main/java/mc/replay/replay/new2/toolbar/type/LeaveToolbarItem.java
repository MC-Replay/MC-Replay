package mc.replay.replay.new2.toolbar.type;

import mc.replay.common.utils.item.ItemBuilder;
import mc.replay.replay.new2.toolbar.ToolbarItem;
import org.bukkit.Material;

public final class LeaveToolbarItem extends ToolbarItem {

    public LeaveToolbarItem(int inventorySlot) {
        super(
                "leave",
                inventorySlot,
                new ItemBuilder(Material.DARK_OAK_DOOR)
                        .displayName("&cLeave replay")
                        .lore(
                                "&7Click here to leave the",
                                "&7replay."
                        )
                        .build()
        );

        this.onClick = (player) -> {
            // TODO leave replay / stop replay if player is navigator
        };
    }
}