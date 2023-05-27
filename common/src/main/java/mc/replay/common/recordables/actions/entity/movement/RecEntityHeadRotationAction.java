package mc.replay.common.recordables.actions.entity.movement;

import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.common.recordables.types.entity.movement.RecEntityHeadRotation;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityHeadRotationPacket;
import mc.replay.wrapper.entity.EntityWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public final class RecEntityHeadRotationAction implements EntityRecordableAction<RecEntityHeadRotation> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntityHeadRotation recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        EntityWrapper entity = data.entity();
        Pos oldPosition = entity.getPosition();

        entity.setPosition(oldPosition.withRotation(recordable.yaw(), oldPosition.pitch()));

        return List.of(
                new ClientboundEntityHeadRotationPacket(
                        data.entityId(),
                        recordable.yaw()
                )
        );
    }
}