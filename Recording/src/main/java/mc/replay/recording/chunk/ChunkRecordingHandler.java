package mc.replay.recording.chunk;

import lombok.Getter;
import mc.replay.api.MCReplayAPI;
import mc.replay.recording.RecordingSessionImpl;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
public final class ChunkRecordingHandler {

    private final Map<RecordingSessionImpl, ChunkRecordingTask> chunkRecordingTasks = new HashMap<>();

    public Collection<Chunk> getRecordableChunks(RecordingSessionImpl recordingSession) {
        ChunkRecordingTask recordingTask = this.chunkRecordingTasks.get(recordingSession);
        return (recordingTask == null) ? Set.of() : recordingTask.getRecordingChunks();
    }

    public Collection<Integer> getDestroyedEntities(RecordingSessionImpl recordingSession) {
        ChunkRecordingTask recordingTask = this.chunkRecordingTasks.get(recordingSession);
        return (recordingTask == null) ? Set.of() : recordingTask.getDestroyedEntities();
    }

    public void startTask(RecordingSessionImpl recordingSession) {
        ChunkRecordingTask chunkRecordingTask = new ChunkRecordingTask(
                this,
                recordingSession,
                recordingSession.getContestant()
        );

        Bukkit.getScheduler().runTaskTimerAsynchronously(MCReplayAPI.getJavaPlugin(), (task) -> {
            if (chunkRecordingTask.shouldContinue(task)) {
                chunkRecordingTask.run();
            }
        }, 0, 1L);

        this.chunkRecordingTasks.put(recordingSession, chunkRecordingTask);
    }
}