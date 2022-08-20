package mc.replay;

import lombok.Getter;
import mc.replay.api.MCReplay;
import mc.replay.api.MCReplayAPI;
import mc.replay.commands.ReplayTestCommand;
import mc.replay.common.CommonInstance;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.dispatcher.ReplayDispatchManager;
import mc.replay.listener.PlayerInteractListener;
import mc.replay.replay.Replay;
import mc.replay.replay.session.ReplaySession;
import mc.replay.storage.ReplayCreator;
import mc.replay.storage.ReplayStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class MCReplayPlugin extends JavaPlugin implements MCReplay {

    @Getter
    private static MCReplayPlugin instance;

    private ReplayStorage replayStorage;
    private ReplayCreator replayCreator;

    private ReplayDispatchManager dispatchManager;

    private Map<Player, ReplaySession> sessions;

    @Override
    public void onLoad() {
        instance = this;

        JavaReflections.getField(MCReplayAPI.class, MCReplay.class, "mcReplay").set(null, this);
        JavaReflections.getField(MCReplayAPI.class, JavaPlugin.class, "javaPlugin").set(null, this);
    }

    @Override
    public void onEnable() {
        CommonInstance.plugin = this;

        this.replayStorage = new ReplayStorage(this);
        this.replayCreator = new ReplayCreator(this);

        this.getCommand("replaytest").setExecutor(new ReplayTestCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);

        this.dispatchManager = new ReplayDispatchManager(this);

        this.sessions = new HashMap<>();
        this.enable();
    }

    public void enable() {
        this.dispatchManager.start();
    }

    public void disable() {
        this.dispatchManager.stop();
    }

    public Replay createReplay(Player player) {
        return this.replayCreator.createReplay(player);
    }
}