package mc.replay.replay.time;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.replay.IReplaySession;
import mc.replay.api.replay.time.IReplaySkipTimeHandler;
import mc.replay.api.replay.time.SkipTimeType;
import mc.replay.common.MCReplayInternal;
import mc.replay.replay.ReplaySession;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.NavigableMap;
import java.util.concurrent.TimeUnit;

public final class ReplaySkipTimeHandler implements IReplaySkipTimeHandler {

    private final SkipTimeForwardsHandler forwardsHandler;
    private final SkipTimeBackwardsHandler backwardsHandler;

    public ReplaySkipTimeHandler(MCReplayInternal instance) {
        this.forwardsHandler = new SkipTimeForwardsHandler(instance);
        this.backwardsHandler = new SkipTimeBackwardsHandler(instance);
    }

    @Override
    public void skipTime(@NotNull IReplaySession iReplaySession, int time, @NotNull TimeUnit unit, @NotNull SkipTimeType type) {
        if (!(iReplaySession instanceof ReplaySession session)) {
            throw new IllegalArgumentException("Session is not a MC-Replay ReplaySession");
        }

        int timeMillis = (int) unit.toMillis(time);
        boolean forwards = type == SkipTimeType.FORWARDS;

        NavigableMap<Integer, List<Recordable>> recordables = session.getRecording().recordables();
        int currentTime = session.getPlayTask().getCurrentTime();

        int until = (forwards) ? Math.min(currentTime + timeMillis, recordables.lastKey()) : Math.max(currentTime - timeMillis, recordables.firstKey());
        if (currentTime == until) return; // No need to skip time

        boolean wasPaused = session.isPaused();
        session.setPaused(true);

        NavigableMap<Integer, List<Recordable>> recordablesBetween = recordables.subMap(
                (forwards) ? currentTime : until,
                true,
                (forwards) ? until : currentTime,
                true
        );

        if (!forwards) {
            recordablesBetween = recordablesBetween.descendingMap();
        }

        if (forwards) {
            this.forwardsHandler.skipTime(session, until, recordablesBetween);
        } else {
            this.backwardsHandler.skipTime(session, until, recordablesBetween);
        }

        session.getPlayTask().setCurrentTime(until);

        if (!wasPaused) {
            session.setPaused(false);
        }
    }
}