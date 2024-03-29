package mc.replay.api.recording;

import mc.replay.api.recordables.Recordable;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

public interface IRecordingSession {

    @NotNull UUID getSessionUuid();

    @NotNull World getWorld();

    long getStartTime();

    @NotNull TreeMap<Integer, List<Recordable>> getRecordables();

    @NotNull IRecording stopRecording();
}