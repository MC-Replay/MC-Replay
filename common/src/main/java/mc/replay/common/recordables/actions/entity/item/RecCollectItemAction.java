package mc.replay.common.recordables.actions.entity.item;

import mc.replay.api.recording.recordables.action.EntityRecordableAction;
import mc.replay.api.recording.recordables.entity.RecordableEntityData;
import mc.replay.common.recordables.types.entity.item.RecCollectItem;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundCollectItemPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.function.Function;

public record RecCollectItemAction() implements EntityRecordableAction<RecCollectItem> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecCollectItem recordable, @UnknownNullability Function<Integer, RecordableEntityData> function) {
        RecordableEntityData collectedEntityData = function.apply(recordable.collectedEntityId().entityId());
        RecordableEntityData collectorEntityData = function.apply(recordable.collectorEntityid().entityId());
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