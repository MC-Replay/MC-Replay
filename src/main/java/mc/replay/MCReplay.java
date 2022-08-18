package mc.replay;

import lombok.Getter;
import mc.replay.commands.ReplayTestCommand;
import mc.replay.dispatcher.event.ReplayEventDispatcher;
import mc.replay.dispatcher.packet.ReplayPacketDispatcher;
import mc.replay.dispatcher.tick.ReplayTickDispatcher;
import mc.replay.listener.PlayerInteractListener;
import mc.replay.replay.Replay;
import mc.replay.replay.session.ReplaySession;
import mc.replay.storage.ReplayStorage;
import mc.replay.storage.ReplayCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class MCReplay extends JavaPlugin {

    @Getter
    private static MCReplay instance;

    private ReplayStorage replayStorage;
    private ReplayCreator replayCreator;

    private ReplayEventDispatcher eventDispatcher;
    private ReplayPacketDispatcher packetDispatcher;
    private ReplayTickDispatcher tickDispatcher;

    private Map<Player, ReplaySession> sessions;

    @Override
    public void onEnable() {
        instance = this;

        this.replayStorage = new ReplayStorage(this);
        this.replayCreator = new ReplayCreator(this);

        this.getCommand("replaytest").setExecutor(new ReplayTestCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);

        this.eventDispatcher = new ReplayEventDispatcher(this);
        this.packetDispatcher = new ReplayPacketDispatcher(this);
        this.tickDispatcher = new ReplayTickDispatcher(this);

        this.sessions = new HashMap<>();
        this.enable();
    }

    public void enable() {
        this.eventDispatcher.start();
        this.packetDispatcher.start();
        this.tickDispatcher.start();
    }

    public void disable() {
        this.eventDispatcher.stop();
        this.packetDispatcher.stop();
        this.tickDispatcher.stop();
    }

    public Replay createReplay(Player player) {
        return this.replayCreator.createReplay(player);
    }
}