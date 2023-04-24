package mc.replay.common.recordables.actions.entity.movement;

import mc.replay.api.recording.recordables.action.EntityRecordableAction;
import mc.replay.api.recording.recordables.data.IEntityProvider;
import mc.replay.api.recording.recordables.entity.RecordableEntityData;
import mc.replay.common.recordables.types.entity.movement.RecEntityHeadRotation;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityHeadRotationPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public record RecEntityHeadRotationAction() implements EntityRecordableAction<RecEntityHeadRotation> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntityHeadRotation recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        provider.rotateEntity(recordable.entityId().entityId(), recordable.yaw(), data.entity().getPosition().pitch());

        return List.of(
                new ClientboundEntityHeadRotationPacket(
                        data.entityId(),
                        recordable.yaw()
                )
        );
    }
}