package mc.replay.api;

import lombok.Getter;
import mc.replay.api.recording.IRecordingHandler;
import mc.replay.api.recording.recordables.IRecordableRegistry;
import mc.replay.api.replay.IReplayHandler;
import mc.replay.api.utils.config.ReplayConfigurationType;
import mc.replay.api.utils.config.SimpleConfigurationFile;
import mc.replay.packetlib.PacketLib;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCReplayAPI {

    @Getter
    private static MCReplay mcReplay;

    public static JavaPlugin getJavaPlugin() {
        return mcReplay.getJavaPlugin();
    }

    public static PacketLib getPacketLib() {
        return mcReplay.getPacketLib();
    }

    public static SimpleConfigurationFile getConfigFile(ReplayConfigurationType fileType) {
        return mcReplay.getConfigFile(fileType);
    }

    public static IRecordingHandler getRecordingHandler() {
        return mcReplay.getRecordingHandler();
    }

    public static IRecordableRegistry getRecordableRegistry() {
        return mcReplay.getRecordableRegistry();
    }

    public static IReplayHandler getReplayHandler() {
        return mcReplay.getReplayHandler();
    }
}