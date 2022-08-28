package mc.replay.recording;

import lombok.Getter;
import mc.replay.api.MCReplayAPI;
import mc.replay.api.recording.Recording;
import mc.replay.api.recording.RecordingSession;
import mc.replay.api.recording.contestant.RecordingContestant;
import mc.replay.api.recording.contestant.def.EverythingRecordingContestant;
import mc.replay.api.recording.recordables.CachedRecordable;
import mc.replay.api.recording.recordables.Recordable;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

@Getter
public final class RecordingSessionImpl implements RecordingSession {

    private final RecordingHandler recordingHandler;

    private final UUID sessionUuid;
    private final RecordingContestant contestant;
    private final long startTime;
    private final NavigableMap<Long, List<CachedRecordable>> recordables;

    private boolean isRecording = true;

    RecordingSessionImpl(RecordingHandler recordingHandler, RecordingContestant contestant) {
        this.recordingHandler = recordingHandler;
        this.sessionUuid = UUID.randomUUID();
        this.contestant = contestant;
        this.startTime = System.currentTimeMillis();
        this.recordables = new TreeMap<>();
    }

    @Override
    public @NotNull Collection<@NotNull Player> getContestants() {
        if (this.contestant instanceof EverythingRecordingContestant) {
            return new HashSet<>(Bukkit.getOnlinePlayers());
        }

        return new HashSet<>(this.contestant.players());
    }

    @Override
    public @NotNull Recording stopRecording() {
        this.isRecording = false;
        return MCReplayAPI.getRecordingHandler().stopRecording(this.sessionUuid);
    }

    @Override
    public boolean isInsideRecordingRange(int chunkX, int chunkZ) {
        Collection<Chunk> recordableChunks = this.recordingHandler.getChunkRecordingHandler().getRecordableChunks(this);
        return recordableChunks.stream().anyMatch((chunk) -> chunk.getX() == chunkX && chunk.getZ() == chunkZ);
    }

    @Override
    public boolean isInsideRecordingRange(int blockX, int blockY, int blockZ) {
        Collection<Chunk> recordableChunks = this.recordingHandler.getChunkRecordingHandler().getRecordableChunks(this);
        return recordableChunks.stream().anyMatch((chunk) -> chunk.equals(chunk.getWorld().getBlockAt(blockX, blockY, blockZ).getChunk()));
    }

    @Override
    public boolean isInsideRecordingRange(int entityId) {
        Collection<Integer> destroyedEntities = this.recordingHandler.getChunkRecordingHandler().getDestroyedEntities(this);
        if (destroyedEntities.contains(entityId)) return false;

        Collection<Chunk> recordableChunks = this.recordingHandler.getChunkRecordingHandler().getRecordableChunks(this);
        return recordableChunks.stream().anyMatch((chunk) -> Arrays.stream(chunk.getEntities()).anyMatch((entity) -> entity.getEntityId() == entityId));
    }

    public boolean addRecordables(List<Recordable<? extends Function<?, ?>>> newRecordables) {
        if (newRecordables == null || newRecordables.isEmpty()) return false;

        long time = System.currentTimeMillis() - this.startTime;
        List<CachedRecordable> recordables = this.recordables.get(time);
        if (recordables == null) recordables = new ArrayList<>();

        for (Recordable<? extends Function<?, ?>> newRecordable : newRecordables) {
            recordables.add(new CachedRecordable(newRecordable));
        }

        this.recordables.put(time, recordables);
        return true;
    }

    public boolean addRecordable(Recordable<? extends Function<?, ?>> newRecordable) {
        if (newRecordable == null) return false;

        return this.addRecordables(List.of(newRecordable));
    }
}