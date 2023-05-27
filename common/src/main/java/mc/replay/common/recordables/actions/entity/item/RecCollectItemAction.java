package mc.replay.common.recordables.actions.entity.item;

import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.common.recordables.types.entity.item.RecCollectItem;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundCollectItemPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public final class RecCollectItemAction implements EntityRecordableAction<RecCollectItem> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecCollectItem recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData collectedEntityData = provider.getEntity(recordable.collectedEntityId().entityId());
        RecordableEntityData collectorEntityData = provider.getEntity(recordable.collectorEntityid().entityId());
        if (collectedEntityData == null || collectorEntityData == null) return List.of();

        return List.of(
                new ClientboundCollectItemPacket(
                        collectedEntityData.entityId(),
                        collectorEntityData.entityId(),
                        recordable.pickupItemCount()
                )
        );
    }
}