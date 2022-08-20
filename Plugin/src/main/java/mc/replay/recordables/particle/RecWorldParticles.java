package mc.replay.recordables.particle;

import mc.replay.common.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_16_R3.ParticleParam;
import org.bukkit.entity.Player;

public record RecWorldParticles(Object particleParam, boolean longDistance, double x, double y, double z, float offsetX,
                                float offsetY, float offsetZ, float particleData,
                                int particleCount) implements ParticleRecordable {

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
    public void play(Player viewer) {
        MinecraftPlayerNMS.sendPacket(viewer, this.createPacket());
    }

    private Object createPacket() {
        return new PacketPlayOutWorldParticles(
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
        );
    }
}