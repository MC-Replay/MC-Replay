package mc.replay.api;

import mc.replay.api.recording.IRecordingHandler;
import mc.replay.api.recording.recordables.IRecordableRegistry;
import mc.replay.api.replay.IReplayHandler;
import mc.replay.api.utils.config.ReplayConfigurationType;
import mc.replay.api.utils.config.SimpleConfigurationFile;
import mc.replay.packetlib.PacketLib;
import org.bukkit.plugin.java.JavaPlugin;

public interface MCReplay {

    IRecordingHandler getRecordingHandler();

    IRecordableRegistry getRecordableRegistry();

    IReplayHandler getReplayHandler();

    SimpleConfigurationFile getConfigFile(ReplayConfigurationType fileType);

    PacketLib getPacketLib();

    JavaPlugin getJavaPlugin();
}