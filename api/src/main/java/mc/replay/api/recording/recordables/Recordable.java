package mc.replay.api.recording.recordables;

import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.Nullable;

public interface Recordable extends ReplayByteBuffer.Writer {

    default @Nullable DependentRecordableData<?> depend() {
        return null;
    }
}