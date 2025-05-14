package mc.replay.replay.new2.toolbar;

import mc.replay.api.replay.session.IReplayPlayer;
import mc.replay.replay.new2.ReplayController;
import mc.replay.replay.session.ReplayPlayer;
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

final class ToolbarListener implements Listener {

    private final ReplayController replayController;
    private final ToolbarService service;

    ToolbarListener(ReplayController replayController, ToolbarService service) {
        this.replayController = replayController;
        this.service = service;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.PHYSICAL)) return;

        Player player = event.getPlayer();
        ReplayPlayer replayPlayer = (ReplayPlayer) this.replayController.getReplayPlayer(player);
        if (replayPlayer == null) return;

        ItemStack stack = player.getInventory().getItemInMainHand();

        ToolbarItem toolbarItem = this.service.getItem(stack);
        if (toolbarItem != null) {
            event.setCancelled(true);
            toolbarItem.getOnClick().accept(replayPlayer);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        IReplayPlayer replayPlayer = this.replayController.getReplayPlayer(event.getPlayer());
        if (replayPlayer == null) return;

        ToolbarItem toolbarItemMain = this.service.getItem(event.getMainHandItem());
        ToolbarItem toolbarItemOffhand = this.service.getItem(event.getOffHandItem());

        if (toolbarItemMain != null || toolbarItemOffhand != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        ReplayPlayer replayPlayer = (ReplayPlayer) this.replayController.getReplayPlayer(event.getWhoClicked().getUniqueId());
        if (replayPlayer == null) return;

        ItemStack stack = event.getCurrentItem();

        ToolbarItem toolbarItem = this.service.getItem(stack);
        if (toolbarItem != null) {
            event.setCancelled(true);
            event.getView().setCursor(null);

            toolbarItem.getOnClick().accept(replayPlayer);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        IReplayPlayer replayPlayer = this.replayController.getReplayPlayer(event.getPlayer());
        if (replayPlayer == null) return;

        ItemStack stack = event.getItemDrop().getItemStack();

        ToolbarItem toolbarItem = this.service.getItem(stack);
        if (toolbarItem != null) {
            event.setCancelled(true);
        }
    }
}