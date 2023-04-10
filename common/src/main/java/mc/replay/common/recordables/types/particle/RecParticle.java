package mc.replay.common.recordables.types.particle;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecParticle(int particleId, boolean longDistance, double x, double y, double z, float offsetX,
                          float offsetY, float offsetZ, float particleData,
                          int particleCount, byte[] data) implements Recordable {

    public RecParticle(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(INT),
                reader.read(BOOLEAN),
                reader.read(DOUBLE),
                reader.read(DOUBLE),
                reader.read(DOUBLE),
                reader.read(FLOAT),
                reader.read(FLOAT),
                reader.read(FLOAT),
                reader.read(FLOAT),
                reader.read(INT),
                reader.read(BYTE_ARRAY)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(INT, this.particleId);
        writer.write(BOOLEAN, this.longDistance);
        writer.write(DOUBLE, this.x);
        writer.write(DOUBLE, this.y);
        writer.write(DOUBLE, this.z);
        writer.write(FLOAT, this.offsetX);
        writer.write(FLOAT, this.offsetY);
        writer.write(FLOAT, this.offsetZ);
        writer.write(FLOAT, this.particleData);
        writer.write(INT, this.particleCount);
        writer.write(BYTE_ARRAY, this.data);
    }
}