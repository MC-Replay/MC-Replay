package mc.replay.common.recordables.types.sound;

import mc.replay.api.recordables.Recordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecSoundEffect(Integer soundId, String soundName, Float range, int sourceId, int x, int y, int z, float volume,
                             float pitch, @Nullable Long seed) implements Recordable {

    public RecSoundEffect(@NotNull ReplayByteBuffer reader) {
        this(
                reader.readOptional(INT),
                reader.readOptional(STRING),
                reader.readOptional(FLOAT),
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
        writer.writeOptional(INT, this.soundId);
        writer.writeOptional(STRING, this.soundName);
        writer.writeOptional(FLOAT, this.range);
        writer.write(INT, this.sourceId);
        writer.write(INT, this.x);
        writer.write(INT, this.y);
        writer.write(INT, this.z);
        writer.write(FLOAT, this.volume);
        writer.write(FLOAT, this.pitch);
        writer.write(LONG, (this.seed == null) ? 0 : this.seed);
    }
}