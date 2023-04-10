package mc.replay.common.recordables.types.sound;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecStopSound(byte flags, int sourceId, String sound) implements Recordable {

    public RecStopSound(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(BYTE),
                reader.read(INT),
                reader.read(STRING)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(BYTE, this.flags);
        writer.write(INT, this.sourceId);
        writer.write(STRING, this.sound);
    }
}