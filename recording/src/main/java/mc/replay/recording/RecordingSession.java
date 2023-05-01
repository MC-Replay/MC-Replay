package mc.replay.recording;

import lombok.Getter;
import mc.replay.api.MCReplayAPI;
import mc.replay.api.recordables.Recordable;
import mc.replay.api.recording.IRecording;
import mc.replay.api.recording.IRecordingSession;
import mc.replay.wrapper.entity.EntityTracker;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

@Getter
public final class RecordingSession implements IRecordingSession {

    private final UUID sessionUuid;
    private final World world;
    private final long startTime;
    private final TreeMap<Integer, List<Recordable>> recordables;
    private final EntityTracker entityTracker;

    RecordingSession(World world) {
        this.sessionUuid = UUID.randomUUID();
        this.world = world;
        this.startTime = System.currentTimeMillis();
        this.recordables = new TreeMap<>();
        this.entityTracker = new EntityTracker();
    }

    @Override
    public @NotNull IRecording stopRecording() {
        return MCReplayAPI.getRecordingHandler().stopRecording(this.sessionUuid);
    }

    public void addRecordables(List<Recordable> newRecordables) {
        if (newRecordables == null || newRecordables.isEmpty()) return;

        int time = (int) (System.currentTimeMillis() - this.startTime);
        List<Recordable> recordables = this.recordables.getOrDefault(time, new ArrayList<>());

        for (Recordable newRecordable : newRecordables) {
            if (newRecordable == null) continue;

            recordables.add(newRecordable);
        }

        this.recordables.put(time, recordables);
    }
}