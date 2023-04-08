package mc.replay;

import lombok.Getter;
import mc.replay.api.MCReplay;
import mc.replay.api.MCReplayAPI;
import mc.replay.classgenerator.ClassGenerator;
import mc.replay.commands.ReplayTestCommand;
import mc.replay.common.CommonInstance;
import mc.replay.common.recordables.RecordableRegistry;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.dispatcher.ReplayDispatchManager;
import mc.replay.packetlib.PacketLib;
import mc.replay.recording.RecordingHandler;
import mc.replay.replay.ReplayHandler;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MCReplayPlugin extends JavaPlugin implements MCReplay {

    @Getter
    private static MCReplayPlugin instance;

    private PacketLib packetLib;

    private RecordingHandler recordingHandler;
    private RecordableRegistry recordableRegistry;

    private ReplayHandler replayHandler;

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

        this.packetLib = new PacketLib(this);
        this.packetLib.inject();

        this.recordingHandler = new RecordingHandler();
        this.recordableRegistry = new RecordableRegistry();

        this.replayHandler = new ReplayHandler(this);

        this.getCommand("replaytest").setExecutor(new ReplayTestCommand());

        this.dispatchManager = new ReplayDispatchManager(this);

        ClassGenerator.generate();

        this.enable();
    }

    public void enable() {
        this.dispatchManager.start();
    }

    public void disable() {
        this.dispatchManager.stop();
    }
}