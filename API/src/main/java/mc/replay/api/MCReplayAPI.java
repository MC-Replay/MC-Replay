package mc.replay.api;

import lombok.Getter;
import mc.replay.api.recording.IRecordingHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCReplayAPI {

    @Getter
    private static MCReplay mcReplay;
    @Getter
    private static JavaPlugin javaPlugin;

    public static IRecordingHandler getRecordingHandler() {
        return mcReplay.getRecordingHandler();
    }
}