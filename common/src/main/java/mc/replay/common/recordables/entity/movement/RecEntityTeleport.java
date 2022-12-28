package mc.replay.common.recordables.entity.movement;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.interfaces.RecordableEntity;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityTeleportPacket;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecEntityTeleport(EntityId entityId, double x, double y, double z, float yaw,
                                float pitch, boolean onGround) implements RecordableEntity {

    public RecEntityTeleport(EntityId entityId, Location to, boolean onGround) {
        this(
                entityId,
                to.getX(),
                to.getY(),
                to.getZ(),
                to.getYaw(),
                to.getPitch(),
                onGround
        );
    }

    public RecEntityTeleport(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(DOUBLE),
                reader.read(DOUBLE),
                reader.read(DOUBLE),
                reader.read(FLOAT),
                reader.read(FLOAT),
                reader.read(BOOLEAN)
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

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(DOUBLE, this.x);
        writer.write(DOUBLE, this.y);
        writer.write(DOUBLE, this.z);
        writer.write(FLOAT, this.yaw);
        writer.write(FLOAT, this.pitch);
        writer.write(BOOLEAN, this.onGround);
    }
}