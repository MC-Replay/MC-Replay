package mc.replay.common.recordables.particle;

import mc.replay.common.recordables.RecordableParticle;
import mc.replay.packetlib.network.packet.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.ClientboundParticlePacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecParticle(int particleId, boolean longDistance, double x, double y, double z, float offsetX,
                          float offsetY, float offsetZ, float particleData,
                          int particleCount, byte[] data) implements RecordableParticle {

    public static RecParticle of(int particleId, boolean longDistance, double x, double y, double z, float offsetX,
                                 float offsetY, float offsetZ, float particleData, int particleCount, byte[] data) {
        return new RecParticle(
                particleId,
                longDistance,
                x,
                y,
                z,
                offsetX,
                offsetY,
                offsetZ,
                particleData,
                particleCount,
                data
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
}