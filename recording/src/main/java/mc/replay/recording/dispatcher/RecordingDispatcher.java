package mc.replay.recording.dispatcher;

import mc.replay.common.MCReplayInternal;
import mc.replay.nms.MCReplayNMS;

public abstract class RecordingDispatcher {

    protected final MCReplayInternal plugin;

    public RecordingDispatcher(MCReplayInternal plugin) {
        this.plugin = plugin;
    }

    public void start() {
    }

    public void stop() {
    }

    public int getCurrentTick() {
        return MCReplayNMS.getInstance().getCurrentServerTick();
    }

    public boolean shouldRecord() {
        return !this.plugin.getRecordingHandler().getRecordingSessions().isEmpty();
    }
}