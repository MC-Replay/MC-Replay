package mc.replay.recording;

import mc.replay.api.recording.Recording;
import mc.replay.api.recording.recordables.CachedRecordable;

import java.time.Duration;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

public record RecordingImpl(String id, Duration duration, long startedAt, long endedAt,
                            TreeMap<Integer, List<CachedRecordable>> recordables) implements Recording {

    public RecordingImpl(long startedAt, TreeMap<Integer, List<CachedRecordable>> recordables) {
        this(
                UUID.randomUUID().toString(),
                Duration.ofMillis(System.currentTimeMillis() - startedAt),
                startedAt,
                System.currentTimeMillis(),
                recordables
        );
    }
}