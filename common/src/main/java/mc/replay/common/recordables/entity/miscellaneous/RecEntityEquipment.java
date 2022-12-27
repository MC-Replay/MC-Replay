package mc.replay.common.recordables.entity.miscellaneous;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityEquipmentPacket;
import mc.replay.wrapper.inventory.ItemWrapper;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public record RecEntityEquipment(EntityId entityId, EquipmentSlot slot,
                                 org.bukkit.inventory.ItemStack itemStack) implements RecordableEntity {

    public static RecEntityEquipment of(EntityId entityId, EquipmentSlot slot, org.bukkit.inventory.ItemStack itemStack) {
        return new RecEntityEquipment(
                entityId,
                slot,
                itemStack
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());

        return List.of(new ClientboundEntityEquipmentPacket(
                data.entityId(),
                Map.of(
                        (byte) this.slot.ordinal(),
                        new ItemWrapper(this.itemStack)
                )
        ));
    }
}