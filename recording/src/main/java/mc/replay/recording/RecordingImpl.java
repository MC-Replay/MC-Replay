package mc.replay.recording;

import mc.replay.api.recording.Recording;
import mc.replay.api.recording.recordables.Recordable;

import java.time.Duration;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

public record RecordingImpl(String id, Duration duration, long startedAt, long endedAt,
                            TreeMap<Integer, List<Recordable>> recordables) implements Recording {

    public RecordingImpl(long startedAt, TreeMap<Integer, List<Recordable>> recordables) {
        this(
                UUID.randomUUID().toString(),
                Duration.ofMillis(System.currentTimeMillis() - startedAt),
                startedAt,
                System.currentTimeMillis(),
                recordables
        );
    }
}