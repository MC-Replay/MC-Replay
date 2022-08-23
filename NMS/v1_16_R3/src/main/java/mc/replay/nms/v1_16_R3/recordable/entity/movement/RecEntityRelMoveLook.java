package mc.replay.nms.v1_16_R3.recordable.entity.movement;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.common.utils.EntityPacketUtils;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntity;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

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
    public @NotNull List<@NotNull Object> createReplayPackets(Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());

        return List.of(new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(
                data.entityId(),
                this.x,
                this.y,
                this.z,
                this.yaw,
                this.pitch,
                this.onGround
        ));
    }
}