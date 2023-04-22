package mc.replay.api.recording;

import mc.replay.api.recording.recordables.Recordable;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.List;
import java.util.NavigableMap;

public interface IRecording {

    @NotNull String id();

    @NotNull Duration duration();

    long startedAt();

    long endedAt();

    @NotNull NavigableMap<Integer, List<Recordable>> recordables();
}