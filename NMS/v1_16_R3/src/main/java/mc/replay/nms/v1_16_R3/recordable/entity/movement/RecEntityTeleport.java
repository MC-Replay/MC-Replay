package mc.replay.nms.v1_16_R3.recordable.entity.movement;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.common.utils.EntityPacketUtils;
import mc.replay.common.utils.reflection.JavaReflections;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityTeleport;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntityTeleport(EntityId entityId, double x, double y, double z, byte yaw,
                                byte pitch, boolean onGround) implements RecordableEntity {

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
    public @NotNull List<@NotNull Object> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());

        try {
            PacketPlayOutEntityTeleport packetPlayOutEntityTeleport = new PacketPlayOutEntityTeleport();

            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "a", int.class).set(packetPlayOutEntityTeleport, data.entityId());
            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "b", double.class).set(packetPlayOutEntityTeleport, this.x);
            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "c", double.class).set(packetPlayOutEntityTeleport, this.y);
            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "d", double.class).set(packetPlayOutEntityTeleport, this.z);
            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "e", byte.class).set(packetPlayOutEntityTeleport, this.yaw);
            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "f", byte.class).set(packetPlayOutEntityTeleport, this.pitch);
            JavaReflections.getField(packetPlayOutEntityTeleport.getClass(), "g", boolean.class).set(packetPlayOutEntityTeleport, this.onGround);

            return List.of(packetPlayOutEntityTeleport);
        } catch (Exception exception) {
            exception.printStackTrace();
            return List.of();
        }
    }
}