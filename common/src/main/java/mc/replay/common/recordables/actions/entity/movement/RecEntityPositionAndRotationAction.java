package mc.replay.common.recordables.actions.entity.movement;

import mc.replay.api.recording.recordables.action.EntityRecordableAction;
import mc.replay.api.recording.recordables.data.IEntityProvider;
import mc.replay.api.recording.recordables.entity.RecordableEntityData;
import mc.replay.common.recordables.types.entity.movement.RecEntityPositionAndRotation;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityPositionAndRotationPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public record RecEntityPositionAndRotationAction() implements EntityRecordableAction<RecEntityPositionAndRotation> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntityPositionAndRotation recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        provider.moveEntity(recordable.entityId().entityId(), recordable.deltaPosition());

        return List.of(
                new ClientboundEntityPositionAndRotationPacket(
                        data.entityId(),
                        recordable.deltaPosition(),
                        recordable.onGround()
                )
        );
    }
}