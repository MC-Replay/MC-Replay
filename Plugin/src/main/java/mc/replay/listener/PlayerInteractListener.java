package mc.replay.listener;

import mc.replay.MCReplayPlugin;
import mc.replay.replay.session.ReplayPlayer;
import mc.replay.replay.session.ReplaySession;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction().equals(Action.PHYSICAL)) return;
        if (event.getHand() == null || event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (event.getItem() == null) return;

        ReplaySession session = MCReplayPlugin.getInstance().getSessions().get(player);
        if (session != null) {
            ReplayPlayer replayPlayer = session.getReplayPlayer(player);
            if (replayPlayer == null) return;

            replayPlayer.clickButton(event.getItem());
        }
    }
}
