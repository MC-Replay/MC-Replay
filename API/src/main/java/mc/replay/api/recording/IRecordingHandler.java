package mc.replay.api.recording;

import mc.replay.api.recording.contestant.RecordingContestant;
import org.jetbrains.annotations.NotNull;

public interface IRecordingHandler {

    @NotNull
    IRecordingSessionHandler getRecordingSessionHandler();

    @NotNull
    RecordingSession startRecording(@NotNull RecordingContestant contestant);
}