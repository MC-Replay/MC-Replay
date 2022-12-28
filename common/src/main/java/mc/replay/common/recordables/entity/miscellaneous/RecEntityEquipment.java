package mc.replay.common.recordables.entity.miscellaneous;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.interfaces.RecordableEntity;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityEquipmentPacket;
import mc.replay.wrapper.inventory.ItemWrapper;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static mc.replay.packetlib.network.ReplayByteBuffer.ITEM;

public record RecEntityEquipment(EntityId entityId, EquipmentSlot slot,
                                 ItemWrapper item) implements RecordableEntity {

    public RecEntityEquipment(EntityId entityId, EquipmentSlot slot, ItemStack itemStack) {
        this(entityId, slot, new ItemWrapper(itemStack));
    }

    public RecEntityEquipment(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.readEnum(EquipmentSlot.class),
                new ItemWrapper(reader.read(ITEM))
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());

        return List.of(new ClientboundEntityEquipmentPacket(
                data.entityId(),
                Map.of(
                        (byte) this.slot.ordinal(),
                        new ItemWrapper(this.item)
                )
        ));
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.writeEnum(EquipmentSlot.class, this.slot);
        writer.write(ITEM, this.item);
    }
}