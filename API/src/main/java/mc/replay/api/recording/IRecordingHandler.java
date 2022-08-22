package mc.replay.api.recording;

import mc.replay.api.recording.contestant.RecordingContestant;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public interface IRecordingHandler {

    Map<UUID, RecordingSession> getRecordingSessions();

    @NotNull
    RecordingSession startRecording(@NotNull RecordingContestant contestant);

    @NotNull Recording stopRecording(@NotNull UUID sessionUuid);

    boolean cancelRecording(@NotNull UUID sessionUuid);
}