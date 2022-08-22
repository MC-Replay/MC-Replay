package mc.replay.api.recording;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public interface IRecordingSessionHandler {

    Map<UUID, RecordingSession> getRecordingSessions();

    @NotNull Recording stopSession(@NotNull UUID sessionUuid);

    boolean cancelSession(@NotNull UUID sessionUuid);
}