package mc.replay.common.recordables.actions.entity.equipment;

import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.common.recordables.types.entity.equipment.RecEntityChestplate;
import mc.replay.nms.entity.RLivingEntity;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityEquipmentPacket;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.Map;

public final class RecEntityChestplateAction implements EntityRecordableAction<RecEntityChestplate> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntityChestplate recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        if (data.entity() instanceof RLivingEntity livingEntityWrapper) {
            livingEntityWrapper.setEquipment(EquipmentSlot.CHEST, recordable.item());
        }

        return List.of(
                new ClientboundEntityEquipmentPacket(
                        data.entityId(),
                        Map.of(
                                (byte) EquipmentSlot.CHEST.ordinal(),
                                recordable.item()
                        )
                )
        );
    }
}