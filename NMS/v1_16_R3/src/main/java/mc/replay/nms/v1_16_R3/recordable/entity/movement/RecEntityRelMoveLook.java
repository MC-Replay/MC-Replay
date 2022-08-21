package mc.replay.nms.v1_16_R3.recordable.entity.movement;

import mc.replay.common.recordables.RecordableEntity;
import mc.replay.common.replay.EntityId;
import mc.replay.common.replay.ReplayEntity;
import mc.replay.common.utils.EntityPacketUtils;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.common.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityTeleport;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public record RecEntityRelMoveLook(EntityId entityId, short x, short y, short z, byte yaw,
                                   byte pitch, boolean onGround, Location current) implements RecordableEntity {

    public static RecEntityRelMoveLook of(EntityId entityId, Location from, Location to, boolean onGround) {
        return new RecEntityRelMoveLook(
                entityId,
                (short) ((to.getX() * 32 - from.getX() * 32) * 128),
                (short) ((to.getY() * 32 - from.getY() * 32) * 128),
                (short) ((to.getZ() * 32 - from.getZ() * 32) * 128),
                EntityPacketUtils.getCompressedAngle(to.getYaw()),
                EntityPacketUtils.getCompressedAngle(to.getPitch()),
                onGround,
                to
        );
    }

    @Override
    public ReplayEntity<?> play(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId) {
        Object packet = this.createMovePacket(entityId);
        MinecraftPlayerNMS.sendPacket(viewer, packet);
        return replayEntity;
    }

    @Override
    public ReplayEntity<?> jumpInTime(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId, boolean forward) {
        Object teleportPacket = this.createTeleportPacket(entityId);
        if (teleportPacket == null) return replayEntity;

        MinecraftPlayerNMS.sendPacket(viewer, teleportPacket);
        return replayEntity;
    }

    private Object createMovePacket(int entityId) {
        return new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(
                entityId,
                this.x,
                this.y,
                this.z,
                this.yaw,
                this.pitch,
                this.onGround
        );
    }

    private Object createTeleportPacket(int entityId) {
        try {
            PacketPlayOutEntityTeleport packetPlayOutEntityTeleport = new PacketPlayOutEntityTeleport();

            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "a", int.class).set(packetPlayOutEntityTeleport, entityId);
            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "b", double.class).set(packetPlayOutEntityTeleport, this.current.getX());
            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "c", double.class).set(packetPlayOutEntityTeleport, this.current.getY());
            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "d", double.class).set(packetPlayOutEntityTeleport, this.current.getZ());
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