package mc.replay.replay.new2.toolbar;

import mc.replay.api.replay.session.IReplayPlayer;
import mc.replay.nms.MCReplayNMS;
import mc.replay.replay.new2.ReplayController;
import mc.replay.replay.new2.toolbar.type.*;
import mc.replay.replay.session.ReplayPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

final class ToolbarService {

    private final Map<String, ToolbarItem> toolbarItems = new HashMap<>();

    ToolbarService(JavaPlugin plugin, ReplayController replayController) {
        plugin.getServer().getPluginManager().registerEvents(new ToolbarListener(replayController, this), plugin);

        this.register(new TeleportToolbarItem(0));
        this.register(new DecreaseSpeedToolbarItem(2));
        this.register(new BackwardsToolbarItem(3));
        this.register(new PauseResumeToolbarItem(4));
        this.register(new ForwardsToolbarItem(5));
        this.register(new IncreaseSpeedToolbarItem(6));
        this.register(new LeaveToolbarItem(8));
    }

    void giveItems(IReplayPlayer replayPlayer) {
        if (replayPlayer.replaySession().isInvalid()) return;

        Player player = replayPlayer.player();
        player.getInventory().clear();

        if (replayPlayer.isNavigator()) {
            for (ToolbarItem toolbarItem : this.toolbarItems.values()) {
                toolbarItem.give((ReplayPlayer) replayPlayer);
            }
        } else {
            this.getItem("leave").give((ReplayPlayer) replayPlayer);
        }
    }

    ToolbarItem getItem(String id) {
        return this.toolbarItems.get(id);
    }

    ToolbarItem getItem(ItemStack itemStack) {
        String id = MCReplayNMS.getInstance().modifyItemStack(itemStack).getTagValue("TOOLBAR_ITEM");
        return id == null ? null : this.toolbarItems.get(id);
    }

    private void register(ToolbarItem item) {
        ToolbarItem oldItem = this.toolbarItems.putIfAbsent(item.getId(), item);
        if (oldItem != null) {
            throw new IllegalStateException("Toolbar item with id " + item.getId() + " already registered");
        }
    }
}