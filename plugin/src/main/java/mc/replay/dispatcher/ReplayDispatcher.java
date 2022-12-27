package mc.replay.dispatcher;

import mc.replay.MCReplayPlugin;
import mc.replay.common.utils.reflection.nms.MinecraftNMS;

public abstract class ReplayDispatcher {

    protected final MCReplayPlugin plugin;

    public ReplayDispatcher(MCReplayPlugin plugin) {
        this.plugin = plugin;
    }

    public MCReplayPlugin getPlugin() {
        return this.plugin;
    }

    public void start() {
    }

    public void stop() {
    }

    public int getCurrentTick() {
        return MinecraftNMS.getCurrentServerTick();
    }

    public boolean shouldRecord() {
        return MCReplayPlugin.getInstance().getRecordingHandler().shouldRecord();
    }
}