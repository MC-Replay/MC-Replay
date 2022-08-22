package mc.replay.replay.session.task;

import lombok.AccessLevel;
import lombok.Getter;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.replay.ReplaySessionImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

@Getter(AccessLevel.PACKAGE)
public final class ReplaySessionPlayTask implements Runnable {

    private final ReplaySessionImpl replaySession;
    private final NavigableMap<Long, List<Recordable>> recordables;

    private final long startTime, endTime;

    private long currentTime;

    public ReplaySessionPlayTask(ReplaySessionImpl replaySession) {
        this.replaySession = replaySession;
        this.recordables = replaySession.getRecording().recordables();

        this.startTime = this.currentTime = this.recordables.firstKey();
        this.endTime = this.recordables.lastKey();
    }

    @Override
    public void run() {
        if (this.replaySession.isPaused()) return;

        List<Recordable> recordables = new ArrayList<>();
        long nextTime = this.currentTime + ((long) (Math.ceil(this.replaySession.getSpeed() * 50D)));
        for (long i = this.currentTime; i < nextTime; i++) {
            recordables.addAll(this.recordables.getOrDefault(i, new ArrayList<>()));
        }

        for (Recordable recordable : recordables) {

        }

        this.currentTime = nextTime;
        if (this.currentTime >= this.endTime) {
            this.replaySession.stop();
        }
    }
}