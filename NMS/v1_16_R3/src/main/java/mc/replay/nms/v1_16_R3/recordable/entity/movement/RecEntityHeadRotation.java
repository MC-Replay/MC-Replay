package mc.replay.nms.v1_16_R3.recordable.entity.movement;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.common.utils.EntityPacketUtils;
import mc.replay.common.utils.reflection.JavaReflections;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityHeadRotation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntityHeadRotation(EntityId entityId, byte yaw) implements RecordableEntity {

    public static RecEntityHeadRotation of(EntityId entityId, float yaw) {
        return new RecEntityHeadRotation(
                entityId,
                EntityPacketUtils.getCompressedAngle(yaw)
        );
    }

    @Override
    public @NotNull List<@NotNull Object> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());

        PacketPlayOutEntityHeadRotation packetPlayOutEntityHeadRotation = new PacketPlayOutEntityHeadRotation();

        JavaReflections.getField(packetPlayOutEntityHeadRotation.getClass(), int.class, "a").set(packetPlayOutEntityHeadRotation, data.entityId());
        JavaReflections.getField(packetPlayOutEntityHeadRotation.getClass(), byte.class, "b").set(packetPlayOutEntityHeadRotation, this.yaw);

        return List.of(packetPlayOutEntityHeadRotation);
    }
}