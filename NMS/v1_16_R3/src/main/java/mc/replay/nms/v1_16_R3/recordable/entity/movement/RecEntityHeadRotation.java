package mc.replay.nms.v1_16_R3.recordable.entity.movement;

import mc.replay.common.recordables.RecordableEntity;
import mc.replay.common.replay.EntityId;
import mc.replay.common.replay.ReplayEntity;
import mc.replay.common.utils.EntityPacketUtils;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.common.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityHeadRotation;
import org.bukkit.entity.Player;

public record RecEntityHeadRotation(EntityId entityId, byte yaw) implements RecordableEntity {

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