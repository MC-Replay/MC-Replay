package mc.replay.common.recordables.actions.entity.movement;

import mc.replay.api.recording.recordables.action.EntityRecordableAction;
import mc.replay.api.recording.recordables.data.IEntityProvider;
import mc.replay.api.recording.recordables.entity.RecordableEntityData;
import mc.replay.common.recordables.types.entity.movement.RecEntityCorrectPositionAndRotation;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityTeleportPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public record RecEntityCorrectPositionAndRotationAction() implements EntityRecordableAction<RecEntityCorrectPositionAndRotation> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntityCorrectPositionAndRotation recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        provider.teleportEntity(recordable.entityId().entityId(), recordable.position());

        return List.of(
                new ClientboundEntityTeleportPacket(
                        data.entityId(),
                        recordable.position(),
                        recordable.onGround()
                )
        );
    }
}