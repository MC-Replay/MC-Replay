package mc.replay.common.recordables.actions.entity.miscellaneous;

import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntityAttach;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityAttachPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public final class RecEntityAttachAction implements EntityRecordableAction<RecEntityAttach> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntityAttach recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData attachedData = provider.getEntity(recordable.attachedEntityId().entityId());
        int holdingEntityId = recordable.holdingEntityId().entityId();
        RecordableEntityData holdingData = (holdingEntityId == -1) ? null : provider.getEntity(holdingEntityId);
        if (attachedData == null) return List.of();

        holdingEntityId = (holdingData != null)
                ? holdingData.entityId()
                : -1; // Detach

        return List.of(
                new ClientboundEntityAttachPacket(
                        attachedData.entityId(),
                        holdingEntityId
                )
        );
    }
}