package mc.replay.common.recordables.types.sound;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecCustomSoundEffect(String soundName, int sourceId, int x, int y, int z, float volume,
                                   float pitch, long seed) implements Recordable {

    public RecCustomSoundEffect(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(STRING),
                reader.read(INT),
                reader.read(INT),
                reader.read(INT),
                reader.read(INT),
                reader.read(FLOAT),
                reader.read(FLOAT),
                reader.read(LONG)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(STRING, this.soundName);
        writer.write(INT, this.sourceId);
        writer.write(INT, this.x);
        writer.write(INT, this.y);
        writer.write(INT, this.z);
        writer.write(FLOAT, this.volume);
        writer.write(FLOAT, this.pitch);
        writer.write(LONG, this.seed);
    }
}