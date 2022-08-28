package mc.replay.api.recording;

import mc.replay.api.recording.recordables.CachedRecordable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.NavigableMap;
import java.util.UUID;

public interface RecordingSession {

    @NotNull UUID getSessionUuid();

    long getStartTime();

    @NotNull NavigableMap<Long, List<CachedRecordable>> getRecordables();

    @NotNull Recording stopRecording();
}