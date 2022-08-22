package mc.replay.recording;

import lombok.Getter;
import mc.replay.api.recording.IRecordingSessionHandler;
import mc.replay.api.recording.Recording;
import mc.replay.api.recording.RecordingSession;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public final class RecordingSessionHandler implements IRecordingSessionHandler {

    private final Map<UUID, RecordingSessionImpl> recordingSessions = new HashMap<>();

    @Override
    public @NotNull Recording stopSession(@NotNull UUID sessionUuid) {
        RecordingSession recordingSession = this.recordingSessions.remove(sessionUuid);
        if (recordingSession == null) {
            throw new IllegalStateException("No recording session found for session uuid '" + sessionUuid + "'");
        }

        return new RecordingImpl(recordingSession.getStartTime(), recordingSession.getRecordables());
    }

    @Override
    public boolean cancelSession(@NotNull UUID sessionUuid) {
        return this.recordingSessions.remove(sessionUuid) != null;
    }

    RecordingSession addSession(RecordingSessionImpl session) {
        this.recordingSessions.put(session.getSessionUuid(), session);
        return session;
    }
}