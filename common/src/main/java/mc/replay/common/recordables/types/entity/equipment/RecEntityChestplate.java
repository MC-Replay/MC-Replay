package mc.replay.common.recordables.types.entity.equipment;

import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.internal.EntityStateRecordable;
import mc.replay.nms.inventory.RItem;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.ITEM;

public record RecEntityChestplate(EntityId entityId, RItem item) implements EntityStateRecordable {

    public RecEntityChestplate(EntityId entityId, ItemStack itemStack) {
        this(entityId, new RItem(itemStack));
    }

    public RecEntityChestplate(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                new RItem(reader.read(ITEM))
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(ITEM, this.item);
    }
}