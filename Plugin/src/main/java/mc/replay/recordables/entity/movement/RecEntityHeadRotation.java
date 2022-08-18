package mc.replay.recordables.entity.movement;

import mc.replay.recordables.entity.EntityId;
import mc.replay.recordables.entity.EntityRecordable;
import mc.replay.replay.entity.ReplayEntity;
import mc.replay.utils.EntityPacketUtils;
import mc.replay.utils.reflection.JavaReflections;
import mc.replay.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityHeadRotation;
import org.bukkit.entity.Player;

public record RecEntityHeadRotation(EntityId entityId, byte yaw) implements EntityRecordable {

    public static RecEntityHeadRotation of(EntityId entityId, float yaw) {
        return new RecEntityHeadRotation(
                entityId,
                EntityPacketUtils.getCompressedAngle(yaw)
        );
    }

    @Override
    public ReplayEntity<?> play(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId) {
        Object packet = this.createPacket(entityId);
        MinecraftPlayerNMS.sendPacket(viewer, packet);
        return replayEntity;
    }

    @Override
    public ReplayEntity<?> jumpInTime(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId, boolean forward) {
        return this.play(viewer, replayEntity, entity, entityId);
    }

    private Object createPacket(int entityId) {
        PacketPlayOutEntityHeadRotation packetPlayOutEntityHeadRotation = new PacketPlayOutEntityHeadRotation();

        JavaReflections.getField(packetPlayOutEntityHeadRotation.getClass(), int.class, "a").set(packetPlayOutEntityHeadRotation, entityId);
        JavaReflections.getField(packetPlayOutEntityHeadRotation.getClass(), byte.class, "b").set(packetPlayOutEntityHeadRotation, this.yaw);

        return packetPlayOutEntityHeadRotation;
    }
}