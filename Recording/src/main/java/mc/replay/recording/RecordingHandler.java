package mc.replay.recording;

import lombok.Getter;
import mc.replay.api.recording.IRecordingHandler;
import mc.replay.api.recording.RecordingSession;
import mc.replay.api.recording.contestant.RecordingContestant;
import org.jetbrains.annotations.NotNull;

@Getter
public final class RecordingHandler implements IRecordingHandler {

    private final RecordingSessionHandler recordingSessionHandler;

    public RecordingHandler() {
        this.recordingSessionHandler = new RecordingSessionHandler();
    }

    @Override
    public @NotNull RecordingSession startRecording(@NotNull RecordingContestant contestant) {
        RecordingSessionImpl recordingSession = new RecordingSessionImpl(contestant);
        return this.recordingSessionHandler.addSession(recordingSession);
    }

    public boolean shouldRecord() {
        return !this.recordingSessionHandler.getRecordingSessions().isEmpty();
    }
}