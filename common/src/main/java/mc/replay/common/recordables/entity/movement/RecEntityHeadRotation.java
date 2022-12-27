package mc.replay.common.recordables.entity.movement;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityHeadRotationPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntityHeadRotation(EntityId entityId, float yaw) implements RecordableEntity {

    public static RecEntityHeadRotation of(EntityId entityId, float yaw) {
        return new RecEntityHeadRotation(
                entityId,
                yaw
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());

        return List.of(new ClientboundEntityHeadRotationPacket(
                data.entityId(),
                this.yaw
        ));
    }
}