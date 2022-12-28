package mc.replay.recording;

import mc.replay.api.MCReplayAPI;
import mc.replay.api.recording.Recording;
import mc.replay.api.recording.recordables.CachedRecordable;
import mc.replay.recording.file.RecordingFormat;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.NavigableMap;
import java.util.UUID;

public record RecordingImpl(String id, Duration duration, long startedAt, long endedAt,
                            NavigableMap<Long, List<CachedRecordable>> recordables) implements Recording {

    public RecordingImpl(long startedAt, NavigableMap<Long, List<CachedRecordable>> recordables) {
        this(
                UUID.randomUUID().toString(),
                Duration.ofMillis(System.currentTimeMillis() - startedAt),
                startedAt,
                System.currentTimeMillis(),
                recordables
        );
    }

    @Override
    public @NotNull File file() {
        return new File(MCReplayAPI.getJavaPlugin().getDataFolder() + "/recordings/" + this.id + RecordingFormat.FILE_EXTENSION);
    }
}