package mc.replay.api.recording;

import mc.replay.api.recording.recordables.CachedRecordable;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.NavigableMap;
import java.util.UUID;

public interface RecordingSession {

    @NotNull UUID getSessionUuid();

    @NotNull World getWorld();

    long getStartTime();

    @NotNull NavigableMap<Long, List<CachedRecordable>> getRecordables();

    @NotNull Recording stopRecording();
}