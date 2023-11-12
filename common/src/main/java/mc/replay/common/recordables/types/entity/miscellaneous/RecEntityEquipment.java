package mc.replay.common.recordables.types.entity.miscellaneous;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.nms.inventory.RItem;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.ITEM;

public record RecEntityEquipment(EntityId entityId, EquipmentSlot slot,
                                 RItem item) implements Recordable {

    public RecEntityEquipment(EntityId entityId, EquipmentSlot slot, ItemStack itemStack) {
        this(entityId, slot, new RItem(itemStack));
    }

    public RecEntityEquipment(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.readEnum(EquipmentSlot.class),
                new RItem(reader.read(ITEM))
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.writeEnum(EquipmentSlot.class, this.slot);
        writer.write(ITEM, this.item);
    }
}