package mc.replay.replay.session.toolbar;

import mc.replay.api.replay.session.IReplayPlayer;
import mc.replay.api.replay.session.toolbar.IToolbarItemHandler;
import mc.replay.common.utils.item.nbt.ItemStackNBT;
import mc.replay.replay.ReplayHandler;
import mc.replay.replay.session.ReplayPlayer;
import mc.replay.replay.session.toolbar.types.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class ToolbarItemHandler implements IToolbarItemHandler, Listener {

    private final Map<String, ToolbarItem> toolbarItems = new HashMap<>();

    public ToolbarItemHandler(ReplayHandler replayHandler, JavaPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(new ToolbarItemListener(replayHandler, this), plugin);

        this.register(new TeleportToolbarItem(0));
        this.register(new DecreaseSpeedToolbarItem(2));
        this.register(new BackwardsToolbarItem(3));
        this.register(new PauseResumeToolbarItem(4));
        this.register(new ForwardsToolbarItem(5));
        this.register(new IncreaseSpeedToolbarItem(6));
        this.register(new LeaveToolbarItem(8));
    }

    @Override
    public void giveItems(@NotNull IReplayPlayer replayPlayer) {
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

    private void register(ToolbarItem item) {
        this.toolbarItems.put(item.getId(), item);
    }

    ToolbarItem getItem(String id) {
        return (id == null) ? null : this.toolbarItems.get(id);
    }

    ToolbarItem getItem(ItemStack stack) {
        return this.getItem(ItemStackNBT.getString(stack, "TOOLBAR_ITEM"));
    }
}
