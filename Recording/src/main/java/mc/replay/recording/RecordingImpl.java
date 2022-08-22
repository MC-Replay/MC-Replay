package mc.replay.recording;

import mc.replay.api.recording.Recording;
import mc.replay.api.recording.recordables.Recordable;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

record RecordingImpl(String id, Duration duration, long startedAt, long endedAt,
                     Map<Long, List<Recordable>> recordables) implements Recording {

    RecordingImpl(long startedAt, Map<Long, List<Recordable>> recordables) {
        this(null, null, startedAt, 0L, recordables);
    }

    RecordingImpl {
        id = UUID.randomUUID().toString(); // TODO
        endedAt = System.currentTimeMillis();
        duration = Duration.between(Instant.ofEpochMilli(startedAt), Instant.ofEpochMilli(endedAt));
    }
}