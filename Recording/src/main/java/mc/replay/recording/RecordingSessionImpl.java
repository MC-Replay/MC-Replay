package mc.replay.recording;

import lombok.Getter;
import mc.replay.api.MCReplayAPI;
import mc.replay.api.recording.Recording;
import mc.replay.api.recording.RecordingSession;
import mc.replay.api.recording.contestant.RecordingContestant;
import mc.replay.api.recording.recordables.CachedRecordable;
import mc.replay.api.recording.recordables.Recordable;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

@Getter
public final class RecordingSessionImpl implements RecordingSession {

    private final UUID sessionUuid;
    private final RecordingContestant contestant;
    private final long startTime;
    private final NavigableMap<Long, List<CachedRecordable>> recordables;

    RecordingSessionImpl(RecordingContestant contestant) {
        this.sessionUuid = UUID.randomUUID();
        this.contestant = contestant;
        this.startTime = System.currentTimeMillis();
        this.recordables = new TreeMap<>();
    }

    @Override
    public @NotNull Recording stopRecording() {
        return MCReplayAPI.getRecordingHandler().stopRecording(this.sessionUuid);
    }

    public void addRecordables(List<Recordable<? extends Function<?, ?>>> newRecordables) {
        if (newRecordables == null || newRecordables.isEmpty()) return;

        long time = System.currentTimeMillis() - this.startTime;
        List<CachedRecordable> recordables = this.recordables.getOrDefault(time, new ArrayList<>());

        for (Recordable<? extends Function<?, ?>> newRecordable : newRecordables) {
            recordables.add(new CachedRecordable(newRecordable));
        }

        this.recordables.put(time, recordables);
    }
}