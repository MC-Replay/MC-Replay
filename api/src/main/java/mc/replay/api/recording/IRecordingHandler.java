package mc.replay.api.recording;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public interface IRecordingHandler {

    Map<UUID, IRecordingSession> getRecordingSessions();

    @NotNull RecordingSessionBuilder createRecordingSession();

    @NotNull IRecording stopRecording(@NotNull UUID sessionUuid);

    boolean cancelRecording(@NotNull UUID sessionUuid);
}