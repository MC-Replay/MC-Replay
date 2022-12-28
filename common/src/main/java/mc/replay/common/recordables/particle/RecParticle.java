package mc.replay.common.recordables.particle;

import mc.replay.common.recordables.interfaces.RecordableParticle;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundParticlePacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecParticle(int particleId, boolean longDistance, double x, double y, double z, float offsetX,
                          float offsetY, float offsetZ, float particleData,
                          int particleCount, byte[] data) implements RecordableParticle {

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
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of(new ClientboundParticlePacket(
                this.particleId,
                this.longDistance,
                this.x,
                this.y,
                this.z,
                this.offsetX,
                this.offsetY,
                this.offsetZ,
                this.particleData,
                this.particleCount,
                this.data
        ));
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