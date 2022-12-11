package mc.replay.api;

import lombok.Getter;
import mc.replay.api.recording.IRecordingHandler;
import mc.replay.api.replay.IReplayHandler;
import mc.replay.packetlib.PacketLib;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCReplayAPI {

    @Getter
    private static MCReplay mcReplay;
    @Getter
    private static JavaPlugin javaPlugin;

    public static PacketLib getPacketLib() {
        return mcReplay.getPacketLib();
    }

    public static IRecordingHandler getRecordingHandler() {
        return mcReplay.getRecordingHandler();
    }

    public static IReplayHandler getReplayHandler() {
        return mcReplay.getReplayHandler();
    }
}