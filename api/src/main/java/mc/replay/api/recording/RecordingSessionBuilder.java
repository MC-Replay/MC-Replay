package mc.replay.api.recording;

import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public interface RecordingSessionBuilder {

    @NotNull RecordingSessionBuilder world(@NotNull World world);

    @NotNull RecordingSessionBuilder world(@NotNull String worldName);

    @NotNull IRecordingSession startRecording();
}