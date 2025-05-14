package mc.replay.common.recordables.actions.entity.miscellaneous;

import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntityEquipment;
import mc.replay.nms.entity.RLivingEntity;
import mc.replay.nms.inventory.RItem;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityEquipmentPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.Map;

public final class RecEntityEquipmentAction implements EntityRecordableAction<RecEntityEquipment> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntityEquipment recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        if (data.entity() instanceof RLivingEntity livingEntity) {
            livingEntity.setEquipment(recordable.slot(), recordable.item());
        }

        return List.of(
                new ClientboundEntityEquipmentPacket(
                        data.entityId(),
                        Map.of(
                                (byte) recordable.slot().ordinal(),
                                new RItem(recordable.item())
                        )
                )
        );
    }
}