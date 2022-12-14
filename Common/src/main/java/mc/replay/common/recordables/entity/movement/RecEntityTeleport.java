package mc.replay.common.recordables.entity.movement;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityTeleportPacket;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntityTeleport(EntityId entityId, double x, double y, double z, float yaw,
                                float pitch, boolean onGround) implements RecordableEntity {

    public static RecEntityTeleport of(EntityId entityId, Location to, boolean onGround) {
        return new RecEntityTeleport(
                entityId,
                to.getX(),
                to.getY(),
                to.getZ(),
                to.getYaw(),
                to.getPitch(),
                onGround
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());

        return List.of(new ClientboundEntityTeleportPacket(
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