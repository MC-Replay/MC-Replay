package mc.replay.recording.file;

import mc.replay.packetlib.utils.ProtocolVersion;

public final class RecordingFormat {

    private RecordingFormat() {
    }

    public static final String FILE_EXTENSION = ".mcrr"; // MC-Replay Recording

    public static final byte RECORDING_VERSION = 1;
    public static final ProtocolVersion MINECRAFT_PROTOCOL_VERSION = ProtocolVersion.getServerVersion();
}