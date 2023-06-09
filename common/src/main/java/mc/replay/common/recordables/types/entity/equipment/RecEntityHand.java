package mc.replay.common.recordables.types.entity.equipment;

import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.internal.EntityStateRecordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.wrapper.item.ItemWrapper;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.ITEM;

public record RecEntityHand(EntityId entityId, ItemWrapper item) implements EntityStateRecordable {

    public RecEntityHand(EntityId entityId, ItemStack itemStack) {
        this(entityId, new ItemWrapper(itemStack));
    }

    public RecEntityHand(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                new ItemWrapper(reader.read(ITEM))
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(ITEM, this.item);
    }
}