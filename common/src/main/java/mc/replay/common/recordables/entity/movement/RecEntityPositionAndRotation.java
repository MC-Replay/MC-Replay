package mc.replay.common.recordables.entity.movement;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityPositionAndRotationPacket;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntityPositionAndRotation(EntityId entityId, Pos newPosition, Pos oldPosition,
                                           boolean onGround) implements RecordableEntity {

    public static RecEntityPositionAndRotation of(EntityId entityId, Location from, Location to, boolean onGround) {
        return new RecEntityPositionAndRotation(
                entityId,
                Pos.from(to),
                Pos.from(from),
                onGround
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());

        return List.of(new ClientboundEntityPositionAndRotationPacket(
                data.entityId(),
                this.newPosition,
                this.oldPosition,
                this.onGround
        ));
    }
}