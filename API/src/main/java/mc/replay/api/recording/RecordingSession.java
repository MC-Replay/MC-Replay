package mc.replay.api.recording;

import mc.replay.api.recording.contestant.RecordingContestant;
import mc.replay.api.recording.recordables.CachedRecordable;
import mc.replay.api.recording.recordables.Recordable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.NavigableMap;
import java.util.UUID;

public interface RecordingSession {

    @NotNull UUID getSessionUuid();

    @NotNull RecordingContestant getContestant();

    @NotNull Collection<@NotNull Player> getContestants();

    long getStartTime();

    @NotNull NavigableMap<Long, List<CachedRecordable>> getRecordables();

    @NotNull Recording stopRecording();

    boolean isInsideRecordingRange(int chunkX, int chunkZ);

    boolean isInsideRecordingRange(int blockX, int blockY, int blockZ);

    boolean isInsideRecordingRange(int entityId);
}