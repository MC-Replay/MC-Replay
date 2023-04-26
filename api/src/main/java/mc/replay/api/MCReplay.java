package mc.replay.api;

import mc.replay.api.recording.IRecordingHandler;
import mc.replay.api.recordables.IRecordableRegistry;
import mc.replay.api.replay.IReplayHandler;
import mc.replay.api.utils.config.IReplayConfigProcessor;
import mc.replay.api.utils.config.templates.ReplayMessages;
import mc.replay.api.utils.config.templates.ReplaySettings;
import mc.replay.packetlib.PacketLib;
import org.bukkit.plugin.java.JavaPlugin;

public interface MCReplay {

    IRecordingHandler getRecordingHandler();

    IRecordableRegistry getRecordableRegistry();

    IReplayHandler getReplayHandler();

    IReplayConfigProcessor<ReplayMessages> getMessagesProcessor();

    IReplayConfigProcessor<ReplaySettings> getSettingsProcessor();

    PacketLib getPacketLib();

    JavaPlugin getJavaPlugin();
}