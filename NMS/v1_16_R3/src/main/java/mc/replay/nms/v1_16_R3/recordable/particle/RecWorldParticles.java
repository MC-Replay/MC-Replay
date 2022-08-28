package mc.replay.nms.v1_16_R3.recordable.particle;

import mc.replay.common.recordables.RecordableParticle;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_16_R3.ParticleParam;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecWorldParticles(Object particleParam, boolean longDistance, double x, double y, double z, float offsetX,
                                float offsetY, float offsetZ, float particleData,
                                int particleCount) implements RecordableParticle {

    public static RecWorldParticles of(Object particleParam, boolean longDistance, double x, double y, double z, float offsetX,
                                       float offsetY, float offsetZ, float particleData, int particleCount) {
        return new RecWorldParticles(
                particleParam,
                longDistance,
                x,
                y,
                z,
                offsetX,
                offsetY,
                offsetZ,
                particleData,
                particleCount
        );
    }

    @Override
    public @NotNull Vector blockPosition() {
        return new Vector(this.x, this.y, this.z);
    }

    @Override
    public @NotNull List<@NotNull Object> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of(new PacketPlayOutWorldParticles(
                (ParticleParam) this.particleParam,
                this.longDistance,
                this.x,
                this.y,
                this.z,
                this.offsetX,
                this.offsetY,
                this.offsetZ,
                this.particleData,
                this.particleCount
        ));
    }
}