package mc.replay.replay.session.toolbar;

import mc.replay.replay.ReplayHandler;
import mc.replay.replay.session.ReplayPlayerImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

record ToolbarItemListener(ReplayHandler replayHandler, ToolbarItemHandler handler) implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.PHYSICAL)) return;

        Player player = event.getPlayer();
        ReplayPlayerImpl replayPlayer = this.replayHandler.getReplayPlayer(player);
        if (replayPlayer == null) return;

        ItemStack stack = player.getInventory().getItemInMainHand();

        ToolbarItem toolbarItem = this.handler.getItem(stack);
        if (toolbarItem != null) {
            event.setCancelled(true);
            toolbarItem.getOnClick().accept(replayPlayer);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSwapHandItem(PlayerSwapHandItemsEvent event) {
        ReplayPlayerImpl replayPlayer = this.replayHandler.getReplayPlayer(event.getPlayer());
        if (replayPlayer == null) return;

        ToolbarItem toolbarItemMain = this.handler.getItem(event.getMainHandItem());
        ToolbarItem toolbarItemOffhand = this.handler.getItem(event.getOffHandItem());

        if (toolbarItemMain != null || toolbarItemOffhand != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        ReplayPlayerImpl replayPlayer = this.replayHandler.getReplayPlayer(event.getWhoClicked().getUniqueId());
        if (replayPlayer == null) return;

        ItemStack stack = event.getCurrentItem();

        ToolbarItem toolbarItem = this.handler.getItem(stack);
        if (toolbarItem != null) {
            event.setCancelled(true);
            event.getView().setCursor(null);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemDrop(PlayerDropItemEvent event) {
        ReplayPlayerImpl replayPlayer = this.replayHandler.getReplayPlayer(event.getPlayer());
        if (replayPlayer == null) return;

        ItemStack stack = event.getItemDrop().getItemStack();

        ToolbarItem toolbarItem = this.handler.getItem(stack);
        if (toolbarItem != null) {
            event.setCancelled(true);
        }
    }
}
