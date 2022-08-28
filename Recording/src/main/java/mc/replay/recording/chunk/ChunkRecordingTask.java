package mc.replay.recording.chunk;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.replay.api.recording.contestant.RecordingContestant;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.utils.ChunkUtils;
import mc.replay.recording.RecordingSessionImpl;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

@AllArgsConstructor
public final class ChunkRecordingTask implements Runnable {

    public static final int CHUNK_RECORDING_RADIUS = 2;

    private final ChunkRecordingHandler chunkRecordingHandler;
    private final RecordingSessionImpl recordingSession;
    private final RecordingContestant contestant;


    @Getter
    private final Collection<Integer> destroyedEntities = Sets.newConcurrentHashSet();
    @Getter
    private final Collection<Chunk> recordingChunks = Sets.newConcurrentHashSet();

    public boolean shouldContinue(BukkitTask task) {
        if (!this.recordingSession.isRecording()) {
            // Cancel this task if the recording is no longer active.
            task.cancel();
            this.chunkRecordingHandler.getChunkRecordingTasks().remove(this.recordingSession);
            return false;
        }

        return true;
    }

    @Override
    public void run() {
        Collection<@NotNull Player> players = new HashSet<>(this.contestant.players());

        if (players.isEmpty()) {
            players.addAll(Bukkit.getOnlinePlayers());
        }

        Collection<Chunk> chunks = new HashSet<>();
        for (Player player : players) {
            if (!player.isOnline()) continue;

            Collection<Chunk> chunksAroundPlayer = ChunkUtils.getChunksAroundPlayer(player, CHUNK_RECORDING_RADIUS);

            for (Chunk chunk : chunksAroundPlayer) {
                if (chunks.contains(chunk)) {
                    // TODO There is another player in this chunk, so we need to stop the fake player of one of these two players with recording.
                    continue;
                }

                chunks.add(chunk);
            }
        }

        this.destroyEntitiesInOldChunks(chunks);
        this.spawnEntitiesInNewChunks(chunks);

        this.recordingChunks.clear();
        this.recordingChunks.addAll(chunks);
    }

    private void destroyEntitiesInOldChunks(Collection<Chunk> newChunks) {
        for (Chunk recordingChunk : this.recordingChunks) {
            if (!newChunks.contains(recordingChunk)) {
                for (Entity entity : recordingChunk.getEntities()) {
                    Recordable<? extends Function<?, ?>> recordable;
                    if (entity instanceof Player player) {
                        // TODO don't destroy contestants
                        recordable = this.recordingSession.getRecordingHandler().getNmsCore().createDestroyPlayerRecordable(player);
                    } else {
                        recordable = this.recordingSession.getRecordingHandler().getNmsCore().createDestroyEntityRecordable(entity);
                    }

                    if (this.recordingSession.addRecordable(recordable)) {
                        this.destroyedEntities.add(entity.getEntityId());
                    }
                }
            }
        }
    }

    private void spawnEntitiesInNewChunks(Collection<Chunk> newChunks) {
        for (Chunk recordingChunk : newChunks) {
            if (!this.recordingChunks.contains(recordingChunk)) {
                for (Entity entity : recordingChunk.getEntities()) {
                    Recordable<? extends Function<?, ?>> recordable;
                    if (entity instanceof Player player) {
                        // TODO don't spawn contestants
                        recordable = this.recordingSession.getRecordingHandler().getNmsCore().createSpawnPlayerRecordable(player);
                    } else {
                        recordable = this.recordingSession.getRecordingHandler().getNmsCore().createSpawnEntityRecordable(entity);
                    }

                    if (this.recordingSession.addRecordable(recordable)) {
                        this.destroyedEntities.remove(entity.getEntityId());
                    }
                }
            }
        }
    }
}