package mc.replay.recording.dispatcher.types;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recording.IRecordingSession;
import mc.replay.common.MCReplayInternal;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.RecordingDispatcher;
import mc.replay.recording.dispatcher.dispatchers.DispatcherTick;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public final class RecordingTickDispatcher extends RecordingDispatcher {

    private BukkitTask task;
    private final Collection<DispatcherTick> tickHandlers = new HashSet<>();

    public RecordingTickDispatcher(MCReplayInternal plugin) {
        super(plugin);
    }

    public void registerTickHandler(DispatcherTick tickHandler) {
        this.tickHandlers.add(tickHandler);
    }

    public int getDispatcherCount() {
        return this.tickHandlers.size();
    }

    @Override
    public void start() {
        this.task = Bukkit.getScheduler().runTaskTimer(this.plugin.getJavaPlugin(), () -> {
            if (!this.shouldRecord()) return;

            long start = System.currentTimeMillis();

            for (DispatcherTick tickHandler : this.tickHandlers) {
                if (System.currentTimeMillis() - start >= 50) {
                    // Don't handle tick handlers when using more than 50 milliseconds
                    break;
                }

                for (IRecordingSession recordingSession : this.plugin.getRecordingHandler().getRecordingSessions().values()) {
                    RecordingSession session = (RecordingSession) recordingSession;
                    List<Recordable> recordables = tickHandler.getRecordables(session, this.getCurrentTick());
                    if (recordables == null) continue;

                    session.addRecordables(recordables);
                }
            }
        }, 0, 1);
    }

    @Override
    public void stop() {
        this.task.cancel();
    }
}