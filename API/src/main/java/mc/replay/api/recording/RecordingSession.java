package mc.replay.api.recording;

import mc.replay.api.recording.contestant.RecordingContestant;
import mc.replay.api.recording.recordables.Recordable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface RecordingSession {

    @NotNull UUID getSessionUuid();

    @NotNull RecordingContestant getContestant();

    long getStartTime();

    @NotNull Map<Long, List<Recordable>> getRecordables();

    void stopRecording();
}