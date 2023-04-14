package mc.replay.api;

import lombok.Getter;
import mc.replay.api.recording.IRecordingHandler;
import mc.replay.api.recording.recordables.IRecordableRegistry;
import mc.replay.api.replay.IReplayHandler;
import mc.replay.api.utils.config.IReplayConfigProcessor;
import mc.replay.api.utils.config.templates.ReplayMessages;
import mc.replay.api.utils.config.templates.ReplaySettings;
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

    public static IReplayConfigProcessor<ReplayMessages> getMessagesProcessor() {
        return mcReplay.getMessagesProcessor();
    }

    public static IReplayConfigProcessor<ReplaySettings> getSettingsProcessor() {
        return mcReplay.getSettingsProcessor();
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