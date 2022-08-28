package mc.replay.recording;

import lombok.Getter;
import mc.replay.api.recording.IRecordingHandler;
import mc.replay.api.recording.Recording;
import mc.replay.api.recording.RecordingSession;
import mc.replay.api.recording.RecordingSessionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Getter
public final class RecordingHandler implements IRecordingHandler {

    private final Map<UUID, RecordingSession> recordingSessions = new HashMap<>();

    @Override
    public @NotNull RecordingSessionBuilder createRecordingSession() {
        return new RecordingSessionBuilderImpl(this);
    }

    @Override
    public @NotNull Recording stopRecording(@NotNull UUID sessionUuid) {
        RecordingSession recordingSession = this.recordingSessions.remove(sessionUuid);
        if (recordingSession == null) {
            throw new IllegalStateException("No recording session found for session uuid '" + sessionUuid + "'");
        }

        return new RecordingImpl(recordingSession.getStartTime(), new TreeMap<>(recordingSession.getRecordables()));
    }

    @Override
    public boolean cancelRecording(@NotNull UUID sessionUuid) {
        return this.recordingSessions.remove(sessionUuid) != null;
    }

    public boolean shouldRecord() {
        return !this.recordingSessions.isEmpty();
    }
}