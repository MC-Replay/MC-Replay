package mc.replay.api;

import mc.replay.api.recording.IRecordingHandler;
import mc.replay.api.replay.IReplayHandler;
import mc.replay.packetlib.PacketLib;

public interface MCReplay {

    IRecordingHandler getRecordingHandler();

    IReplayHandler getReplayHandler();

    PacketLib getPacketLib();
}