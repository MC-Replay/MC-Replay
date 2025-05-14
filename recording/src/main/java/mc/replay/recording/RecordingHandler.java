package mc.replay.recording;

import lombok.Getter;
import mc.replay.api.recording.IRecording;
import mc.replay.api.recording.IRecordingHandler;
import mc.replay.api.recording.IRecordingSession;
import mc.replay.api.recording.RecordingSessionBuilder;
import mc.replay.recording.file.RecordingFileProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public final class RecordingHandler implements IRecordingHandler {

    private final Map<UUID, IRecordingSession> recordingSessions = new HashMap<>();

    private final RecordingFileProcessor fileProcessor;

    public RecordingHandler() {
        this.fileProcessor = new RecordingFileProcessor();
    }

    @Override
    public @NotNull RecordingSessionBuilder createRecordingSession() {
        return new RecordingSessionBuilderImpl(this);
    }

    @Override
    public @NotNull IRecording stopRecording(@NotNull UUID sessionUuid) {
        IRecordingSession recordingSession = this.recordingSessions.remove(sessionUuid);
        if (recordingSession == null) {
            throw new IllegalStateException("No recording session found for session uuid '" + sessionUuid + "'");
        }

        Recording recording = new Recording(recordingSession.getStartTime(), recordingSession.getRecordables());
        this.fileProcessor.createRecordingFile(recording);
        return recording;
    }

    @Override
    public boolean cancelRecording(@NotNull UUID sessionUuid) {
        return this.recordingSessions.remove(sessionUuid) != null;
    }
}