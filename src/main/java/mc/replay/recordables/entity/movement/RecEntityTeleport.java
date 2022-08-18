package mc.replay.recordables.entity.movement;

import mc.replay.recordables.entity.EntityId;
import mc.replay.recordables.entity.EntityRecordable;
import mc.replay.replay.entity.ReplayEntity;
import mc.replay.utils.EntityPacketUtils;
import mc.replay.utils.reflection.JavaReflections;
import mc.replay.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityTeleport;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public record RecEntityTeleport(EntityId entityId, double x, double y, double z, byte yaw,
                                byte pitch, boolean onGround) implements EntityRecordable {

    public static RecEntityTeleport of(EntityId entityId, Location to, boolean onGround) {
        return new RecEntityTeleport(
                entityId,
                to.getX(),
                to.getY(),
                to.getZ(),
                EntityPacketUtils.getCompressedAngle(to.getYaw()),
                EntityPacketUtils.getCompressedAngle(to.getPitch()),
                onGround
        );
    }

    @Override
    public ReplayEntity<?> play(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId) {
        Object packet = this.createPacket(entityId);
        if (packet == null) return replayEntity;

        MinecraftPlayerNMS.sendPacket(viewer, packet);
        return replayEntity;
    }

    @Override
    public ReplayEntity<?> jumpInTime(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId, boolean forward) {
        return this.play(viewer, replayEntity, entity, entityId);
    }

    private Object createPacket(int entityId) {
        try {
            PacketPlayOutEntityTeleport packetPlayOutEntityTeleport = new PacketPlayOutEntityTeleport();

            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "a", int.class).set(packetPlayOutEntityTeleport, entityId);
            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "b", double.class).set(packetPlayOutEntityTeleport, this.x);
            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "c", double.class).set(packetPlayOutEntityTeleport, this.y);
            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "d", double.class).set(packetPlayOutEntityTeleport, this.z);
            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "e", byte.class).set(packetPlayOutEntityTeleport, this.yaw);
            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "f", byte.class).set(packetPlayOutEntityTeleport, this.pitch);
            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "g", boolean.class).set(packetPlayOutEntityTeleport, this.onGround);

            return packetPlayOutEntityTeleport;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}