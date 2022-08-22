package mc.replay.api;

import mc.replay.api.recording.IRecordingHandler;
import mc.replay.api.replay.IReplayHandler;

public interface MCReplay {

    IRecordingHandler getRecordingHandler();

    IReplayHandler getReplayHandler();
}