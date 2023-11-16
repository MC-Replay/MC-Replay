package mc.replay.replay.time;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.replay.IReplaySession;
import mc.replay.api.replay.time.IReplayJumpTimeHandler;
import mc.replay.api.replay.time.JumpTimeType;
import mc.replay.common.MCReplayInternal;
import mc.replay.replay.ReplaySession;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.NavigableMap;
import java.util.concurrent.TimeUnit;

public final class ReplayJumpTimeHandler implements IReplayJumpTimeHandler {

    private final JumpTimeForwardsHandler forwardsHandler;
    private final JumpTimeBackwardsHandler backwardsHandler;

    public ReplayJumpTimeHandler(MCReplayInternal instance) {
        this.forwardsHandler = new JumpTimeForwardsHandler(instance);
        this.backwardsHandler = new JumpTimeBackwardsHandler(instance);
    }

    @Override
    public void jumpTime(@NotNull IReplaySession iReplaySession, int time, @NotNull TimeUnit unit, @NotNull JumpTimeType type) {
        if (!(iReplaySession instanceof ReplaySession session)) {
            throw new IllegalArgumentException("Session is not a MC-Replay ReplaySession");
        }

        int timeMillis = (int) unit.toMillis(time);
        boolean forwards = type == JumpTimeType.FORWARDS;

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

        if (forwards) {
            this.forwardsHandler.jumpTime(session, until, recordablesBetween);
        } else {
            this.backwardsHandler.jumpTime(session, until, recordablesBetween);
        }

        session.getPlayTask().setCurrentTime(until);

        session.setPaused(wasPaused);
    }
}