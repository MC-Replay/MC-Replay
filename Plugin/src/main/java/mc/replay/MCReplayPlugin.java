package mc.replay;

import lombok.Getter;
import mc.replay.api.MCReplay;
import mc.replay.api.MCReplayAPI;
import mc.replay.commands.ReplayTestCommand;
import mc.replay.common.CommonInstance;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.dispatcher.ReplayDispatchManager;
import mc.replay.recording.RecordingHandler;
import mc.replay.replay.Replay;
import mc.replay.replay.ReplayHandler;
import mc.replay.storage.ReplayCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MCReplayPlugin extends JavaPlugin implements MCReplay {

    @Getter
    private static MCReplayPlugin instance;

    private RecordingHandler recordingHandler;
    private ReplayHandler replayHandler;

    private ReplayCreator replayCreator;

    private ReplayDispatchManager dispatchManager;

    @Override
    public void onLoad() {
        instance = this;

        JavaReflections.getField(MCReplayAPI.class, MCReplay.class, "mcReplay").set(null, this);
        JavaReflections.getField(MCReplayAPI.class, JavaPlugin.class, "javaPlugin").set(null, this);
    }

    @Override
    public void onEnable() {
        CommonInstance.plugin = this;

        this.recordingHandler = new RecordingHandler();
        this.replayHandler = new ReplayHandler(this);

        this.replayCreator = new ReplayCreator(this);

        this.getCommand("replaytest").setExecutor(new ReplayTestCommand());

        this.dispatchManager = new ReplayDispatchManager(this);

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