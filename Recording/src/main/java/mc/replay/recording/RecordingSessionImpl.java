package mc.replay.recording;

import lombok.Getter;
import mc.replay.api.MCReplayAPI;
import mc.replay.api.recording.RecordingSession;
import mc.replay.api.recording.contestant.RecordingContestant;
import mc.replay.api.recording.recordables.Recordable;

import java.util.*;

@Getter
public final class RecordingSessionImpl implements RecordingSession {

    private final UUID sessionUuid;
    private final RecordingContestant contestant;
    private final long startTime;
    private final NavigableMap<Long, List<Recordable>> recordables;

    RecordingSessionImpl(RecordingContestant contestant) {
        this.sessionUuid = UUID.randomUUID();
        this.contestant = contestant;
        this.startTime = System.currentTimeMillis();
        this.recordables = new TreeMap<>();
    }

    @Override
    public void stopRecording() {
        MCReplayAPI.getRecordingHandler().stopRecording(this.sessionUuid);
    }

    public void addRecordables(List<Recordable> newRecordables) {
        if (newRecordables == null || newRecordables.isEmpty()) return;

        long time = System.currentTimeMillis() - this.startTime;
        List<Recordable> recordables = this.recordables.getOrDefault(time, new ArrayList<>());

        recordables.addAll(newRecordables);
        this.recordables.put(time, recordables);
    }
}