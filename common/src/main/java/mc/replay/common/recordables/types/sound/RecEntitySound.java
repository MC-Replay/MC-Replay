package mc.replay.common.recordables.types.sound;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.FLOAT;
import static mc.replay.packetlib.network.ReplayByteBuffer.INT;

public record RecEntitySound(int soundId, int sourceId, int entityId, float volume,
                             float pitch) implements Recordable {

    public RecEntitySound(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(INT),
                reader.read(INT),
                reader.read(INT),
                reader.read(FLOAT),
                reader.read(FLOAT)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(INT, this.soundId);
        writer.write(INT, this.sourceId);
        writer.write(INT, this.entityId);
        writer.write(FLOAT, this.volume);
        writer.write(FLOAT, this.pitch);
    }
}