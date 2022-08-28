package mc.replay.api.recording;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public interface IRecordingHandler {

    Map<UUID, RecordingSession> getRecordingSessions();

    @NotNull RecordingSessionBuilder createRecordingSession();

    @NotNull Recording stopRecording(@NotNull UUID sessionUuid);

    boolean cancelRecording(@NotNull UUID sessionUuid);
}