package mc.replay.replay.preparation;

import mc.replay.api.replay.session.ReplayPlayer;
import mc.replay.api.utils.config.templates.ReplayMessages;
import mc.replay.common.MCReplayInternal;
import mc.replay.common.utils.text.TextFormatter;
import mc.replay.replay.ReplayHandler;
import mc.replay.replay.session.ReplayPlayerImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public final class ReplayPlayerPreparationHandler implements Listener {

    private final ReplayHandler replayHandler;
    private final MCReplayInternal instance;

    private final Map<Player, PlayerState> playerStates = new HashMap<>();

    public ReplayPlayerPreparationHandler(ReplayHandler replayHandler, MCReplayInternal instance) {
        this.replayHandler = replayHandler;
        this.instance = instance;

        Bukkit.getPluginManager().registerEvents(this, instance.getJavaPlugin());
    }

    public void prepare(ReplayPlayer replayPlayer) {
        Player player = replayPlayer.player();

        this.playerStates.put(player, new PlayerState(player));

        PlayerState.DEFAULT_STATE.apply(player);
        this.replayHandler.getToolbarItemHandler().giveItems(replayPlayer);

        TextFormatter.of(this.instance.getMessagesProcessor().getString(ReplayMessages.REPLAY_STARTED)).send(player);

        this.instance.getPacketLib().inject(player);
    }

    public void reset(ReplayPlayer replayPlayer) {
        Player player = replayPlayer.player();

        this.instance.getPacketLib().uninject(player);

        PlayerState state = this.playerStates.remove(player);
        if (state != null) {
            state.apply(player);
        }

        TextFormatter.of(this.instance.getMessagesProcessor().getString(ReplayMessages.REPLAY_STOPPED)).send(player);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlayerQuit(PlayerQuitEvent event) {
        this.quitOrKick(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlayerKick(PlayerKickEvent event) {
        this.quitOrKick(event.getPlayer());
    }

    private void quitOrKick(Player player) {
        if (!this.playerStates.containsKey(player)) return;

        ReplayPlayerImpl replayPlayer = this.replayHandler.getReplayPlayer(player);
        if (replayPlayer == null) return;

        this.reset(replayPlayer);
    }
}