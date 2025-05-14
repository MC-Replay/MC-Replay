package mc.replay.replay.new2.toolbar.type;

import mc.replay.common.MCReplayInternal;
import mc.replay.common.utils.item.ItemBuilder;
import mc.replay.replay.session.menu.ReplayTeleportMenu;
import mc.replay.replay.new2.toolbar.ToolbarItem;
import org.bukkit.Material;

public final class TeleportToolbarItem extends ToolbarItem {

    public TeleportToolbarItem(int inventorySlot) {
        super(
                "teleport",
                inventorySlot,
                new ItemBuilder(Material.COMPASS)
                        .displayName("&aTeleport")
                        .lore(
                                "&7Click here to teleport",
                                "&7to a player."
                        )
                        .build()
        );

        this.onClick = (player) -> {
            MCReplayInternal instance = player.replaySession().getInstance();
            instance.getMenuHandler().openMenu(new ReplayTeleportMenu(player.replaySession()), player.player());
        };
    }
}