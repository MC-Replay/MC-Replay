package mc.replay.common.recordables.actions.entity.movement;

import mc.replay.api.recording.recordables.action.EntityRecordableAction;
import mc.replay.api.recording.recordables.entity.RecordableEntityData;
import mc.replay.common.recordables.types.entity.movement.RecEntityCorrectPositionAndRotation;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityTeleportPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntityCorrectPositionAndRotationAction() implements EntityRecordableAction<RecEntityCorrectPositionAndRotation> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntityCorrectPositionAndRotation recordable, @NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(recordable.entityId().entityId());
        if (data == null) return List.of();

        return List.of(
                new ClientboundEntityTeleportPacket(
                        data.entityId(),
                        recordable.position(),
                        recordable.onGround()
                )
        );
    }
}